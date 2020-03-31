package com.tgt.springmockkdemo.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.UUIDTable
import org.jetbrains.exposed.sql.transactions.transaction
import reactor.core.publisher.toMono
import java.util.*

data class Meal(val id: UUID?, val name: String, val tableNumber: Int?)

class MealDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MealDao>(MealTable) {
        fun cook(menuItem: MenuItem): Meal = transaction {
            MealDao.new { name = menuItem.name }.toModel()
        }

        fun runFood(meal: Meal, tableNumber: Int): Meal = transaction {
            MealDao[meal.id!!].tableNumber = tableNumber
            MealDao[meal.id].toModel()
        }

        fun getMeal(id: UUID): Meal = transaction {
            MealDao[id].toModel()
        }

        private fun MealDao.mealToDao(menuItem: MenuItem, tableNumber: Int) {
            this.name = menuItem.name
            this.tableNumber = tableNumber
        }
    }

    var name by MealTable.name
    var tableNumber by MealTable.tableNumber

    fun toModel() = Meal(this.id.value, this.name, this.tableNumber)
}

internal object MealTable : UUIDTable(name = "meal") {
    val name = text("name").references(MenuTable.name)
    val tableNumber = integer("table_number").nullable()
}