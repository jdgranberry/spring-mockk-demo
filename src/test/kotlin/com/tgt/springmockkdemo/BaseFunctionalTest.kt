package com.tgt.springmockkdemo

import com.tgt.springmockkdemo.model.MealTable
import com.tgt.springmockkdemo.model.MenuTable
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

open class BaseFunctionalTest {
    init {
        Database.connect(EmbeddedPostgres.start().postgresDatabase)
        transaction {
                SchemaUtils.create(MenuTable, MealTable)
        }
    }


}