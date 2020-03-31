package com.tgt.springmockkdemo.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.UUIDTable
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

data class MenuItem(val id: UUID?, val name: String)

class MenuDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MenuDao>(MenuTable) {
        fun getMenu(): List<String> = transaction {
            MenuDao.wrapRows(MenuTable.slice(MenuTable.columns).selectAll().withDistinct()).map { it.name }
        }

        fun insertMenuItem(name: String): MenuItem = transaction {
            MenuDao.new { this.name = name }.toModel()
        }

        fun getMenuItem(name: String): MenuItem? = transaction {
            find { MenuTable.name eq name }.map { it.toModel() }.firstOrNull()
        }
    }

    var name by MenuTable.name

    fun toModel() = MenuItem(this.id.value, name)
}

internal object MenuTable : UUIDTable("menu") {
    val name = text("name").index(isUnique = true)
}