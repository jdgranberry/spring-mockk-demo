package com.tgt.springmockkdemo

import com.tgt.springmockkdemo.model.MenuDao
import com.tgt.springmockkdemo.service.CookService
import com.tgt.springmockkdemo.service.OrderService
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class OrderServiceTest {
    @MockK
    lateinit var cookService: CookService

    lateinit var orderService: OrderService

    @BeforeAll
    fun setUp() {
        orderService = OrderService(cookService)
    }

    @AfterEach
    fun clearMocks() = clearAllMocks()

    @Test
    fun `calling getOrderForTwo calls cookService_cookMeal twice`() {
        val order = "JUICY_LUCY_BURGER"

        val mockMenuDao = mockkObject(MenuDao)

        every { MenuDao.getMenu() } returns listOf(order)

        every { cookService.cookMeal(order, any()) } returnsMany listOf(true, true)

        orderService.placeOrderForTwo(order, 42)

        verify(exactly = 2) { cookService.cookMeal(any(), any()) }

        unmockkObject(MenuDao)
    }


}