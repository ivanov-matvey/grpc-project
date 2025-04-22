package dev.matvenoid.models.users

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.kotlin.datetime.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UsersTable: LongIdTable("users") {
    val phone = varchar("phone", 10)
    val name = varchar("name", 50)
    val birthday = date("birthday")
}

class UserDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserDAO>(UsersTable)

    var phone by UsersTable.phone
    var name by UsersTable.name
    var birthday by UsersTable.birthday
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: UserDAO) = User(
    dao.id.value,
    dao.phone,
    dao.name,
    dao.birthday
)
