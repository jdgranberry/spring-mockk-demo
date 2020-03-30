package com.tgt.springmockkdemo.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.UUIDTable
import java.util.*

data class Meal(val name: String, val tableNumber: Int)

class MealDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MealDao>(MealTable) {
        fun cook(menuItem: MenuItem, tableNumber: Int) {

        }
    }

    val name by MealTable.name
    val tableNumber by MealTable.tableNumber

    fun toModel() = Meal(this.name, this.tableNumber)
}

internal object MealTable : UUIDTable(name = "meal") {
    val name = text("name").references(MenuTable.name)
    val tableNumber = integer("table_number")
}