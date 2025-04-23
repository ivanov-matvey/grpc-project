package dev.matvenoid.models.reservations

class ReservationRepository {
    suspend fun getAll(): List<Reservation> = suspendTransaction {
        ReservationDAO.all().map(::daoToModel)
    }
}
