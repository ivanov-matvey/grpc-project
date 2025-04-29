package dev.matvenoid.repositories

import dev.matvenoid.models.review.Review
import dev.matvenoid.models.review.ReviewDAO
import dev.matvenoid.models.review.daoToModel
import org.jetbrains.exposed.sql.transactions.transaction

class ReviewRepository {
    fun getAll(): List<Review> = transaction {
        ReviewDAO.Companion.all().map(::daoToModel)
    }
}