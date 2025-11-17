package es.undersounds.gc01.users.dtos.users

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Payload para la creación de un usuario")
data class CreateUserDTO(
    @field:Schema(description = "Nombre real del usuario", example = "Jhon Lennon")
    val name: String,

    @field:Schema(description = "Nombre de usuario", example = "JhonLennon")
    val username: String,

    @field:Schema(description = "Contraseña del usuario. No se guarda en el backend, se manda directamente al IdP", example = "123456789")
    val password: String,

    @field:Schema(description = "Biografía del usuario", example = "Desarrollador y amante de la música.")
    val bio: String
)