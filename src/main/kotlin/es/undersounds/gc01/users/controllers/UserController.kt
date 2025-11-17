package es.undersounds.gc01.users.controllers

import es.undersounds.gc01.content.dtos.SuccessDTO
import es.undersounds.gc01.users.controllers.specs.UserControllerSpec
import es.undersounds.gc01.users.dtos.users.CreateUserDTO
import es.undersounds.gc01.users.dtos.users.LoginUserDTO
import es.undersounds.gc01.users.dtos.users.UpdateUserDTO
import es.undersounds.gc01.users.dtos.users.UserCredentialsDTO
import es.undersounds.gc01.users.dtos.users.UserDTO
import es.undersounds.gc01.users.security.AuthenticatedUser
import es.undersounds.gc01.users.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

class UserController (
    userService: UserService
) : UserControllerSpec {
    override fun registerUser(
        newUser: CreateUserDTO,
        pfp: MultipartFile
    ): ResponseEntity<SuccessDTO<UserCredentialsDTO>> {
        val credentials = userService.registerUser(newUser, pfp)
        return ResponseEntity.created()
            .body(SuccessDTO(data = credentials))
    }

    override fun loginUser(user: LoginUserDTO): ResponseEntity<SuccessDTO<UserCredentialsDTO>> {
        TODO("Not yet implemented")
    }

    override fun getUser(username: String): ResponseEntity<SuccessDTO<UserDTO>> {
        TODO("Not yet implemented")
    }

    override fun updateUser(
        user: AuthenticatedUser,
        userUpdate: UpdateUserDTO,
        pfp: MultipartFile?
    ): ResponseEntity<SuccessDTO<UserCredentialsDTO>> {
        TODO("Not yet implemented")
    }

    override fun deleteUser(user: AuthenticatedUser): ResponseEntity<SuccessDTO<Unit>> {
        TODO("Not yet implemented")
    }
}