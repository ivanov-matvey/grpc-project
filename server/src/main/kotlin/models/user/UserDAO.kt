package dev.matvenoid.models.user

import dev.matvenoid.models.reservation.ReservationDAO
import dev.matvenoid.models.reservation.ReservationTable
import dev.matvenoid.models.review.ReviewDAO
import dev.matvenoid.models.review.ReviewTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UserTable: LongIdTable("users") {
    val phone = varchar("phone", 10)
    val name = varchar("name", 50)
    val birthday = date("birthday")
}

class UserDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserDAO>(UserTable)

    var phone by UserTable.phone
    var name by UserTable.name
    var birthday by UserTable.birthday

    val reservations by ReservationDAO referrersOn ReservationTable.userId
    val reviews by ReviewDAO referrersOn ReviewTable.userId
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: UserDAO) = User(
    dao.id.value,
    dao.phone,
    dao.name,
    dao.birthday
)
