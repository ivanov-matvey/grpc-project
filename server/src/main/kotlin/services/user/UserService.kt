package dev.matvenoid.services.user

import dev.matvenoid.proto.GetUsersRequest
import dev.matvenoid.proto.GetUsersResponse
import dev.matvenoid.proto.User
import dev.matvenoid.proto.UserServiceGrpc
import dev.matvenoid.repositories.UserRepository
import io.grpc.stub.StreamObserver
import org.slf4j.Logger

class UserService(val logger: Logger) : UserServiceGrpc.UserServiceImplBase() {
    private val userRepository = UserRepository()

    override fun getUsers(
        request: GetUsersRequest,
        responseObserver: StreamObserver<GetUsersResponse>
    ) {
        try {
            val users = userRepository.getAll()

            val userMessages = users.map { user ->
                User.newBuilder()
                    .setId(user.id)
                    .setName(user.name)
                    .setPhone(user.phone)
                    .setBirthday(user.birthday.toString())
                    .build()
            }

            val response = GetUsersResponse.newBuilder()
                .addAllUsers(userMessages)
                .build()

            responseObserver.onNext(response)
            responseObserver.onCompleted()
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }

}
