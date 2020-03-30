package com.tgt.springmockkdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyResaurant

fun main(args: Array<String>) {
    runApplication<MyResaurant>(*args)
}
