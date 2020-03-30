package com.tgt.springmockkdemo.service

import com.tgt.springmockkdemo.api.RecipeApi
import com.tgt.springmockkdemo.model.MealDao
import com.tgt.springmockkdemo.model.MenuDao
import com.tgt.springmockkdemo.model.MenuItem
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClientException

class CookService(val recipeApi: RecipeApi) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun cookMeal(name: String, tableNumber: Int): Boolean {
        try {
            recipeApi.getRecipe(name)
        } catch (e: WebClientException) {
            logger.error("Unable to get recipe for $name")
            return false
        }

        val menuItem: MenuItem? = MenuDao.getMenuItem(name)?: run {
            logger.error("$name is not on the menu and cannot be made for table $tableNumber.")
            return false
        }

        MealDao.cook(menuItem!!, tableNumber)
        return true
    }
}