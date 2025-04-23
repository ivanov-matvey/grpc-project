package dev.matvenoid.models.reviews

class ReviewRepository {
    suspend fun getAll(): List<Review> = suspendTransaction {
        ReviewDAO.all().map(::daoToModel)
    }
}
