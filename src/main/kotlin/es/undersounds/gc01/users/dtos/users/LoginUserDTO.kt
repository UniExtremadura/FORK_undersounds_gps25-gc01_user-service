package es.undersounds.gc01.users.dtos.users

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Datos del usuario del cual obtener credenciales de acceso")
class LoginUserDTO (
    @field:Schema(description = "Nombre de usuario", example = "jhonlennon")
    val username: String,

    @field:Schema(description = "Contraseña del usuario", example = "123456789")
    val password: String
)