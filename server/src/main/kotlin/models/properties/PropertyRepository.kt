package dev.matvenoid.models.properties

class PropertyRepository {
    suspend fun getAll(): List<Property> = suspendTransaction {
        PropertyDAO.all().map(::daoToModel)
    }
}
