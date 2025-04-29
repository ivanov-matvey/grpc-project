package dev.matvenoid.repositories

import dev.matvenoid.models.property.Property
import dev.matvenoid.models.property.PropertyDAO
import dev.matvenoid.models.property.daoToModel
import dev.matvenoid.models.property.suspendTransaction

class PropertyRepository {
    suspend fun getAll(): List<Property> = suspendTransaction {
        PropertyDAO.Companion.all().map(::daoToModel)
    }
}