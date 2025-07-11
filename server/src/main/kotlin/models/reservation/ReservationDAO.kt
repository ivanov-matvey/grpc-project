package dev.matvenoid.models.reservation

import dev.matvenoid.models.property.PropertyDAO
import dev.matvenoid.models.property.PropertyTable
import dev.matvenoid.models.user.UserDAO
import dev.matvenoid.models.user.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object ReservationTable: LongIdTable("reservations") {
    val startDate = date("start_date")
    val endDate = date("end_date")
    val userId = reference("user_id", UserTable)
    val propertyId = reference("property_id", PropertyTable)
}

class ReservationDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ReservationDAO>(ReservationTable)

    var startDate by ReservationTable.startDate
    var endDate by ReservationTable.endDate
    var user by UserDAO referencedOn ReservationTable.userId
    var property by PropertyDAO referencedOn ReservationTable.propertyId
}

fun daoToModel(dao: ReservationDAO) = Reservation(
    dao.id.value,
    dao.startDate,
    dao.endDate,
    dao.user.id.value,
    dao.property.id.value,
)