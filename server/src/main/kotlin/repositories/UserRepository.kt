package dev.matvenoid.repositories

import dev.matvenoid.models.user.User
import dev.matvenoid.models.user.UserDAO
import dev.matvenoid.models.user.daoToModel
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun getAll(): List<User> = transaction {
        UserDAO.Companion.all().map(::daoToModel)
    }

    fun create(user: User): User = transaction {
        daoToModel(
            UserDAO.new {
                phone = user.phone
                name = user.name
                birthday = user.birthday
            }
        )
    }

    fun update(user: User): User? = transaction {
        daoToModel(
            UserDAO.findByIdAndUpdate(user.id) {
                it.phone = user.phone
                it.name = user.name
                it.birthday = user.birthday
            } ?: return@transaction null
        )
    }

    fun getById(id: Long): User? = transaction {
        daoToModel(
            UserDAO.findById(id)
                ?: return@transaction null
        )
    }

    fun delete(id: Long): Boolean = transaction {
        UserDAO.findById(id)?.delete()
            ?: return@transaction false
        return@transaction true
    }
}