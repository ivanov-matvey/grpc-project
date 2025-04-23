package dev.matvenoid.models.reviews

import kotlinx.datetime.LocalDateTime

data class Review (
    val id: Long,
    val rating: Int,
    var comment: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val userId: Long,
    val propertyId: Long
)
