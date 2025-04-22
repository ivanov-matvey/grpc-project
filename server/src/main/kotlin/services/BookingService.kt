package dev.matvenoid.services

import dev.matvenoid.models.users.UserRepository
import dev.matvenoid.proto.BookingServiceGrpcKt
import dev.matvenoid.proto.User
import dev.matvenoid.proto.GetUsersRequest
import dev.matvenoid.proto.GetUsersResponse

class BookingService: BookingServiceGrpcKt.BookingServiceCoroutineImplBase() {
    val userRepository = UserRepository()

    override suspend fun getUsers(request: GetUsersRequest): GetUsersResponse {
        val users = userRepository.getAll()

        val userMessages = users.map { user ->
            User.newBuilder()
                .setId(user.id)
                .setName(user.name)
                .setPhone(user.phone)
                .setBirthday(user.birthday.toString())
                .build()
        }

        return GetUsersResponse.newBuilder()
            .addAllUsers(userMessages)
            .build()
    }
}