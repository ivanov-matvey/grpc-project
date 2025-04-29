package dev.matvenoid.repositories

import dev.matvenoid.models.review.Review
import dev.matvenoid.models.review.ReviewDAO
import dev.matvenoid.models.review.daoToModel
import dev.matvenoid.models.review.suspendTransaction

class ReviewRepository {
    suspend fun getAll(): List<Review> = suspendTransaction {
        ReviewDAO.Companion.all().map(::daoToModel)
    }
}