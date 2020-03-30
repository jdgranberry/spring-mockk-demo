package com.tgt.springmockkdemo.controller

import com.tgt.springmockkdemo.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class Server(val orderService: OrderService) {

    @GetMapping("/menu")
    fun getMenu(): List<String> {
        return orderService.getMenu()
    }

    @GetMapping("/order")
    fun order(@RequestParam order: String, @RequestParam tableNumber: Int): ResponseEntity<HttpStatus> {
        return if (orderService.placeOrder(order, tableNumber)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}