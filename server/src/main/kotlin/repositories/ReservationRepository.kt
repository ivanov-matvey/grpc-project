package dev.matvenoid.repositories

import dev.matvenoid.models.reservation.Reservation
import dev.matvenoid.models.reservation.ReservationDAO
import dev.matvenoid.models.reservation.daoToModel
import org.jetbrains.exposed.sql.transactions.transaction

class ReservationRepository {
    fun getAll(): List<Reservation> = transaction {
        ReservationDAO.Companion.all().map(::daoToModel)
    }
}