package com.tgt.springmockkdemo.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AppConfig {
    @Value("\${external-apis.recipes}")
    val recipeApiUrl: String? = null

    @Bean
    fun recipeApi(): WebClient = WebClient.builder().baseUrl(recipeApiUrl!!).build()

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerKotlinModule().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
    }
}