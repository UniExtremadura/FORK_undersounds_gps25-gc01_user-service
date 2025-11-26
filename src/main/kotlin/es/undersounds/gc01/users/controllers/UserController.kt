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
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("users")
class UserController (
    private val userService: UserService
) : UserControllerSpec {
    override fun registerUser(
        newUser: CreateUserDTO,
        pfp: MultipartFile
    ): ResponseEntity<SuccessDTO<UserCredentialsDTO>> {
        val credentials = userService.registerUser(newUser, pfp)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.CREATED.value(),
                message = "Usuario registrado correctamente",
                data = credentials
            ),
            HttpStatus.CREATED
        )
    }

    override fun loginUser(user: LoginUserDTO): ResponseEntity<SuccessDTO<UserCredentialsDTO>> {
        val credentials = userService.loginUser(user)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.CREATED.value(),
                message = "Credenciales obtenidas correctamente",
                data = credentials
            ),
            HttpStatus.CREATED
        )
    }

    override fun getUser(username: String): ResponseEntity<SuccessDTO<UserDTO>> {
        val user = userService.getUserByUsername(username)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.CREATED.value(),
                message = "Usuario obtenido correctamente",
                data = user
            ),
            HttpStatus.CREATED
        )
    }

    override fun updateUser(
        user: AuthenticatedUser,
        userUpdate: UpdateUserDTO,
        pfp: MultipartFile?
    ): ResponseEntity<SuccessDTO<UserDTO>> {
        val user = userService.updateUser(user, userUpdate, pfp)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.OK.value(),
                message = "Usuario actualizado correctamente",
                data = user
            ),
            HttpStatus.OK
        )
    }

    override fun deleteUser(user: AuthenticatedUser): ResponseEntity<SuccessDTO<Unit>> {
        TODO("Not yet implemented")
    }
}