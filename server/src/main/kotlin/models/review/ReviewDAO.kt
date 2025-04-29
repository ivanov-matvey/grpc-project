package dev.matvenoid.models.review

import dev.matvenoid.models.property.PropertyDAO
import dev.matvenoid.models.property.PropertyTable
import dev.matvenoid.models.user.UserDAO
import dev.matvenoid.models.user.UserTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object ReviewTable: LongIdTable("reviews") {
    val rating = integer("rating")
    val comment = varchar("comment", 255)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
    val userId = reference("user_id", UserTable)
    val propertyId = reference("property_id", PropertyTable)
}

class ReviewDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ReviewDAO>(ReviewTable)

    var rating by ReviewTable.rating
    var comment by ReviewTable.comment
    var createdAt by ReviewTable.createdAt
    var updatedAt by ReviewTable.updatedAt

    var user by UserDAO referencedOn ReviewTable.userId
    var property by PropertyDAO referencedOn ReviewTable.propertyId
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: ReviewDAO) = Review(
    dao.id.value,
    dao.rating,
    dao.comment,
    dao.createdAt,
    dao.updatedAt,
    dao.user.id.value,
    dao.property.id.value
)