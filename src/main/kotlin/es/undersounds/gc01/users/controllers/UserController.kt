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

    override fun postUser(
        user: AuthenticatedUser,
        dto: CreateUserDTO,
        pfp: MultipartFile
    ): ResponseEntity<SuccessDTO<UserDTO>> {
        val userDTO = userService.postUser(user, dto, pfp)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.CREATED.value(),
                message = "Credenciales obtenidas correctamente",
                data = userDTO
            ),
            HttpStatus.CREATED
        )
    }

    override fun getUser(user: AuthenticatedUser): ResponseEntity<SuccessDTO<UserDTO>> {
        val userDTO = userService.getUser(user)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.OK.value(),
                message = "Usuario retornado correctamente",
                data = userDTO
            ),
            HttpStatus.CREATED
        )
    }

    override fun getUserByUsername(username: String): ResponseEntity<SuccessDTO<UserDTO>> {
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
        val userDTO = userService.updateUser(user, userUpdate, pfp)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.OK.value(),
                message = "Usuario actualizado correctamente",
                data = userDTO
            ),
            HttpStatus.OK
        )
    }

    override fun deleteUser(user: AuthenticatedUser): ResponseEntity<SuccessDTO<Unit>> {
        TODO("Not yet implemented")
    }
}