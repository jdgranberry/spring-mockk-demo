package com.tgt.springmockkdemo.service

import com.tgt.springmockkdemo.model.Meal
import com.tgt.springmockkdemo.model.MealDao
import com.tgt.springmockkdemo.model.MenuDao
import org.springframework.stereotype.Service

@Service
class OrderService(val cookService: CookService) {

    fun getMenu(): List<String> {
        return MenuDao.getMenu()
    }

    fun placeOrder(order: String, tableNumber: Int): Boolean {
        return if (getMenu().contains(order)) {
            cookService.cookMeal(order, tableNumber)
            true
        } else {
            false
        }
    }
}