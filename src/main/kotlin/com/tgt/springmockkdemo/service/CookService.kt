package com.tgt.springmockkdemo.service

import com.tgt.springmockkdemo.api.RecipeApiClient
import com.tgt.springmockkdemo.kafka.FoodRunner
import com.tgt.springmockkdemo.model.Meal
import com.tgt.springmockkdemo.model.MealDao
import com.tgt.springmockkdemo.model.MenuDao
import com.tgt.springmockkdemo.model.MenuItem
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClientException

@Service
class CookService(val recipeApiClient: RecipeApiClient, val foodRunner: FoodRunner) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun cookMeal(name: String, tableNumber: Int): Boolean {
        try {
            recipeApiClient.getRecipe(name)
        } catch (e: WebClientException) {
            logger.error("Unable to get recipe for $name", e)
            return false
        }

        val menuItem: MenuItem? = MenuDao.getMenuItem(name) ?: run {
            logger.error("$name is not on the menu and cannot be made for table $tableNumber.")
            return false
        }

        val meal: Meal? = MealDao.cook(menuItem!!, tableNumber)

        meal.let {
            foodRunner.runFood(it!!)
        } ?: run {
            return false
        }

        return true
    }
}