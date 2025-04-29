package dev.matvenoid.repositories

import dev.matvenoid.models.property.Property
import dev.matvenoid.models.property.PropertyDAO
import dev.matvenoid.models.property.daoToModel
import org.jetbrains.exposed.sql.transactions.transaction

class PropertyRepository {
    fun getAll(): List<Property> = transaction {
        PropertyDAO.Companion.all().map(::daoToModel)
    }
}