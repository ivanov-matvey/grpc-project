package dev.matvenoid.models.users

import kotlinx.datetime.LocalDate

data class User(
    val id: Long,
    val phone: String,
    val name: String,
    val birthday: LocalDate
)