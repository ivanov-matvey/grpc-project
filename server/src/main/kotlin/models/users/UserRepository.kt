package dev.matvenoid.models.users

class UserRepository {
    suspend fun getAll(): List<User> = suspendTransaction {
        UserDAO.all().map(::daoToModel)
    }
}