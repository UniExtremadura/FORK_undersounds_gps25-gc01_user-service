package es.undersounds.gc01.users.controllers.specs

import es.undersounds.gc01.content.dtos.ErrorDTO
import es.undersounds.gc01.content.dtos.SuccessDTO
import es.undersounds.gc01.users.dtos.users.CreateUserDTO
import es.undersounds.gc01.users.dtos.users.LoginUserDTO
import es.undersounds.gc01.users.dtos.users.UpdateUserDTO
import es.undersounds.gc01.users.dtos.users.UserCredentialsDTO
import es.undersounds.gc01.users.dtos.users.UserDTO
import es.undersounds.gc01.users.security.AuthenticatedUser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios en la plataforma, incluyendo registro, inicio de sesión, actualización y eliminación de cuentas.")
interface UserControllerSpec {
    @Operation(
        summary = "Crea un nuevo usuario",
        description = "Permite a un usuario autenticado crear un álbum subiendo metadatos y una portada. Devuelve el álbum completo con información del artista y canciones."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            ApiResponse(
                responseCode = "400",
                description = "Error en los datos enviados",
                content = [Content(schema = Schema(implementation = ErrorDTO::class))]
            )
        ]
    )
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('internal_service')")
    fun registerUser(
        @Parameter(description = "Datos del usuario a crear")
        @Valid newUser: CreateUserDTO,

        @Parameter(description = "Foto de perfil del usuario")
        @RequestPart("pfp") pfp: MultipartFile
    ): ResponseEntity<SuccessDTO<UserCredentialsDTO>>

    @Operation(
        summary = "Inicia sesión con las credenciales de un usuario",
        description = "Inicia sesión con las credenciales de un usuario para obtener un token JWT con el que autenticarse en la plataforma"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuario logueado correctamente"),
            ApiResponse(
                responseCode = "400",
                description = "Error en los datos enviados",
                content = [Content(schema = Schema(implementation = ErrorDTO::class))]
            )
        ]
    )
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], value = ["/login"])
    @PreAuthorize("hasRole('internal_service')")
    fun loginUser(
        @Parameter(description = "Datos del usuario a loguear")
        @Valid @RequestPart("data") user: LoginUserDTO
    ): ResponseEntity<SuccessDTO<UserCredentialsDTO>>

    @Operation(
        summary = "Obtener un usuario por su nombre de usuario",
        description = "Obtiene la información pública de un usuario a partir de su nombre de usuario"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuario retornado correctamente"),
            ApiResponse(
                responseCode = "400",
                description = "Error en los datos enviados",
                content = [Content(schema = Schema(implementation = ErrorDTO::class))]
            )
        ]
    )
    @GetMapping("/public/{username}")
    fun getUser(
        @Parameter(description = "Nombre de usuario del usuario a obtener")
        @PathVariable username: String
    ): ResponseEntity<SuccessDTO<UserDTO>>

    @Operation(
        summary = "Actualiza los datos personales de un usuario",
        description = "Actualiza los datos personales de un usuario autenticado, como su nombre o biografía. También permite actualizar la foto de perfil."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            ApiResponse(
                responseCode = "400",
                description = "Error en los datos enviados",
                content = [Content(schema = Schema(implementation = ErrorDTO::class))]
            )
        ]
    )
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateUser(
        @AuthenticationPrincipal user: AuthenticatedUser,

        @Parameter(description = "Datos del usuario a loguear")
        @Valid userUpdate: UpdateUserDTO,

        @Parameter(description = "Foto de perfil del usuario (opcional)")
        @RequestPart("pfp", required = false) pfp: MultipartFile?
    ): ResponseEntity<SuccessDTO<UserCredentialsDTO>>

    @Operation(
        summary = "Elimina un usuario",
        description = "Elimina el usuario autenticado de la plataforma. Esta acción es irreversible."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            ApiResponse(
                responseCode = "400",
                description = "Error en los datos enviados",
                content = [Content(schema = Schema(implementation = ErrorDTO::class))]
            )
        ]
    )
    @DeleteMapping
    fun deleteUser(
        @AuthenticationPrincipal user: AuthenticatedUser
    ): ResponseEntity<SuccessDTO<Unit>>
}