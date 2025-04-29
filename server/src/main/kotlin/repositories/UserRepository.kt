package dev.matvenoid.repositories

import dev.matvenoid.models.user.User
import dev.matvenoid.models.user.UserDAO
import dev.matvenoid.models.user.daoToModel
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun getAll(): List<User> = transaction {
        UserDAO.Companion.all().map(::daoToModel)
    }
}