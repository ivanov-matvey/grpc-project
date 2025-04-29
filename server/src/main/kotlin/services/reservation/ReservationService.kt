package dev.matvenoid.services.reservation

import dev.matvenoid.proto.GetReservationsRequest
import dev.matvenoid.proto.GetReservationsResponse
import dev.matvenoid.proto.Reservation
import dev.matvenoid.proto.ReservationServiceGrpc
import dev.matvenoid.repositories.ReservationRepository
import io.grpc.stub.StreamObserver
import org.slf4j.Logger

class ReservationService(val logger: Logger) : ReservationServiceGrpc.ReservationServiceImplBase() {
    private val reservationRepository = ReservationRepository()

    override fun getReservations(
        request: GetReservationsRequest,
        responseObserver: StreamObserver<GetReservationsResponse>
    ) {
        try {
            val reservations = reservationRepository.getAll()

            val reservationMessages = reservations.map { reservation ->
                Reservation.newBuilder()
                    .setId(reservation.id)
                    .setStartDate(reservation.startDate.toString())
                    .setEndDate(reservation.endDate.toString())
                    .setUserId(reservation.userId)
                    .setPropertyId(reservation.propertyId)
                    .build()
            }

            val response = GetReservationsResponse.newBuilder()
                .addAllReservation(reservationMessages)
                .build()

            responseObserver.onNext(response)
            responseObserver.onCompleted()
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }
}
