package com.tgt.springmockkdemo.controller

import com.tgt.springmockkdemo.model.Meal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class Server {

    @GetMapping("/meal")
    fun getMenu(): List<Meal> {
        return Meal.values().asList()
    }
    @GetMapping("/order")
    fun getDinner(@RequestParam order: String) {

    }
}