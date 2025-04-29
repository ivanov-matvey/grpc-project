package dev.matvenoid.models.property

import dev.matvenoid.models.reservation.ReservationDAO
import dev.matvenoid.models.reservation.ReservationTable
import dev.matvenoid.models.review.ReviewDAO
import dev.matvenoid.models.review.ReviewTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object PropertyTable: LongIdTable("properties") {
    val address = varchar("address", 100)
    val price = decimal("price", 8, 2)
    val description = varchar("description", 255)
    val isAvailable = bool("is_available")
}

class PropertyDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PropertyDAO>(PropertyTable)

    var address by PropertyTable.address
    var price by PropertyTable.price
    var description by PropertyTable.description
    var isAvailable by PropertyTable.isAvailable

    val reservations by ReservationDAO referrersOn ReservationTable.propertyId
    val reviews by ReviewDAO referrersOn ReviewTable.propertyId
}

fun daoToModel(dao: PropertyDAO) = Property(
    dao.id.value,
    dao.address,
    dao.price.toFloat(),
    dao.description,
    dao.isAvailable
)