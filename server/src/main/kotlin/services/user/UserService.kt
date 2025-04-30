package dev.matvenoid.services.user

import dev.matvenoid.models.user.User
import dev.matvenoid.proto.Empty
import dev.matvenoid.proto.ListUsersResponse
import dev.matvenoid.proto.UserIdRequest
import dev.matvenoid.proto.User as ProtoUser
import dev.matvenoid.proto.UserRequest
import dev.matvenoid.proto.UserResponse
import dev.matvenoid.proto.UserServiceGrpc
import dev.matvenoid.repositories.UserRepository
import io.grpc.stub.StreamObserver
import io.grpc.Status
import kotlinx.datetime.LocalDate
import org.slf4j.Logger

class UserService(val logger: Logger) : UserServiceGrpc.UserServiceImplBase() {
    private val userRepository = UserRepository()

    override fun listUsers(
        request: Empty,
        responseObserver: StreamObserver<ListUsersResponse>
    ) {
        try {
            val users = userRepository.getAll()

            val userMessages = users.map { user ->
                ProtoUser.newBuilder()
                    .setId(user.id)
                    .setName(user.name)
                    .setPhone(user.phone)
                    .setBirthday(user.birthday.toString())
                    .build()
            }

            val response = ListUsersResponse.newBuilder()
                .addAllUsers(userMessages)
                .build()

            responseObserver.onNext(response)
            responseObserver.onCompleted()
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }

    override fun createUser(
        request: UserRequest,
        responseObserver: StreamObserver<UserResponse>
    ) {
        try {
            val userRequest = User(
                id = request.id,
                name = request.name,
                phone = request.phone,
                birthday = LocalDate.parse(request.birthday),
            )

            val user = userRepository.create(userRequest)

            val response = buildUserResponse(user)

            if (response == null) {
                responseObserver.onError(
                    Status.NOT_FOUND
                        .withDescription("Error user creation")
                        .asRuntimeException()
                )
                return
            }

            responseObserver.onNext(response)
            responseObserver.onCompleted()
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }

    override fun updateUser(
        request: UserRequest,
        responseObserver: StreamObserver<UserResponse>
    ) {
        try {
            val userRequest = User(
                id = request.id,
                name = request.name,
                phone = request.phone,
                birthday = LocalDate.parse(request.birthday),
            )

            val user = userRepository.update(userRequest)

            val response = buildUserResponse(user)

            if (response == null) {
                responseObserver.onError(
                    Status.NOT_FOUND
                        .withDescription("User not found")
                        .asRuntimeException()
                )
                return
            }

            responseObserver.onNext(response)
            responseObserver.onCompleted()
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }

    override fun getUser(
        request: UserIdRequest,
        responseObserver: StreamObserver<UserResponse>
    ) {
        try {
            val id = request.id

            val user = userRepository.getById(id)

            val response = buildUserResponse(user)

            if (response == null) {
                responseObserver.onError(
                    Status.NOT_FOUND
                        .withDescription("User not found")
                        .asRuntimeException()
                )
                return
            }

            responseObserver.onNext(response)
            responseObserver.onCompleted()
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }

    override fun deleteUser(
        request: UserIdRequest,
        responseObserver: StreamObserver<Empty>
    ) {
        val id = request.id

        val userDeleted = userRepository.delete(id)

        if (!userDeleted) {
            responseObserver.onError(
                Status.NOT_FOUND
                    .withDescription("User not found")
                    .asRuntimeException()
            )
            return
        }

        val response = Empty.newBuilder().build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}

private fun buildUserResponse(user: User?): UserResponse? {
    if (user == null) {
        return null
    }

    val userMessage = ProtoUser.newBuilder()
        .setId(user.id)
        .setName(user.name)
        .setPhone(user.phone)
        .setBirthday(user.birthday.toString())
        .build()

    val response = UserResponse.newBuilder()
        .setUser(userMessage)
        .build()

    return response
}
