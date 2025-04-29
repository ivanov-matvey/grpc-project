package dev.matvenoid.models.reservation

import kotlinx.datetime.LocalDate

data class Reservation (
    val id: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val userId: Long,
    val propertyId: Long
)
