package dev.matvenoid.models.user

import kotlinx.datetime.LocalDate

data class User(
    val id: Long,
    val phone: String,
    val name: String,
    val birthday: LocalDate
)
