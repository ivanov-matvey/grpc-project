package dev.matvenoid.repositories

import dev.matvenoid.models.user.User
import dev.matvenoid.models.user.UserDAO
import dev.matvenoid.models.user.daoToModel
import dev.matvenoid.models.user.suspendTransaction

class UserRepository {
    suspend fun getAll(): List<User> = suspendTransaction {
        UserDAO.Companion.all().map(::daoToModel)
    }
}