package dev.matvenoid.core

import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        Database.Companion.connect(
            "jdbc:postgresql://localhost:5001/booking",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "postgres"
        )
    }
}