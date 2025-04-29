package dev.matvenoid.repositories

import dev.matvenoid.models.reservation.Reservation
import dev.matvenoid.models.reservation.ReservationDAO
import dev.matvenoid.models.reservation.daoToModel
import dev.matvenoid.models.reservation.suspendTransaction

class ReservationRepository {
    suspend fun getAll(): List<Reservation> = suspendTransaction {
        ReservationDAO.Companion.all().map(::daoToModel)
    }
}