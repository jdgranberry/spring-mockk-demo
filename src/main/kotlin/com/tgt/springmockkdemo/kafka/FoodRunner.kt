package com.tgt.springmockkdemo.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.tgt.springmockkdemo.model.Meal
import com.tgt.springmockkdemo.model.MealDao
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class FoodRunner(val objectMapper: ObjectMapper, val publisher: KafkaTemplate<String, ByteArray>) {
    @Value("\${spring.kafka.producer.topic}")
    private val topic: String? = null
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun runFood(meal: Meal, tableNumber: Int) {
        try {
            publisher.send(topic!!, objectMapper.writeValueAsBytes(meal))
            logger.info("Ran ${meal.name} to table ${tableNumber}.")
            MealDao.runFood(meal, tableNumber)
        } catch (e: Exception) {
            logger.error("Unable to run $meal to ${meal.tableNumber}", e)
        }
    }
}