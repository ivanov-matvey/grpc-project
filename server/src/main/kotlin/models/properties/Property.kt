package dev.matvenoid.models.properties

data class Property(
    val id: Long,
    val address: String,
    val price: Float,
    val description: String,
    val isAvailable: Boolean
)
