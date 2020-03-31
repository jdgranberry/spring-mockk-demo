package com.tgt.springmockkdemo

import com.ninjasquad.springmockk.MockkBean
import com.tgt.springmockkdemo.kafka.FoodRunner
import com.tgt.springmockkdemo.model.Meal
import com.tgt.springmockkdemo.model.MealDao
import com.tgt.springmockkdemo.model.MenuDao
import io.mockk.every
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FoodRunnerDaoFuncTest : BaseFunctionalTest() {
    @MockkBean(relaxed = true, relaxUnitFun = true)
    lateinit var producer: KafkaTemplate<String, ByteArray>

    @Autowired
    lateinit var foodRunner: FoodRunner

    @Test
    fun `running a cooked Meal causes its database entry to be updated with the table number`() {
        val menuItem = MenuDao.insertMenuItem("PESTO_GNOCCHI")
        val actual = MealDao.cook(menuItem)
        val tableNumber = 42

        assertEquals(null, MealDao.getMeal(actual.id!!).tableNumber)

        foodRunner.runFood(actual, tableNumber)

        val result = MealDao.getMeal(actual.id!!)
        assertEquals(tableNumber, result.tableNumber)
    }

    @Test
    fun `FoodRunner publishes a Meal with the correct table number`() {
        val menuItem = MenuDao.insertMenuItem("LOTZZA_MOZZA_PIZZA")
        val actual = MealDao.cook(menuItem)
        val expected = actual.copy(tableNumber = 311)

        val byteArraySlot = slot<ByteArray>()

        every { producer.send(any(), capture(byteArraySlot)) }

        foodRunner.runFood(actual, expected.tableNumber!!)

        val result = foodRunner.objectMapper.readValue(byteArraySlot.captured, Meal::class.java)

        assertEquals(expected, result)
    }
}