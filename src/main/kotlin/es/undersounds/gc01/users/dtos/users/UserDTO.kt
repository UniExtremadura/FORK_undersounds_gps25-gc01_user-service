package es.undersounds.gc01.users.dtos.users

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Información pública de un usuario. El campo `id` se toma del JWT proporcionado por Keycloak.")
data class UserDTO(
    @field:Schema(description = "Nombre real del usuario", example = "Juan Pérez")
    val name: String,

    @field:Schema(description = "Nombre de usuario único", example = "juanperez")
    val username: String,

    @field:Schema(description = "Biografía del usuario", example = "Desarrollador y amante de la música.")
    val bio: String,

    @field:Schema(description = "URL de la foto de perfil del usuario", example = "https://cdn.example.com/users/juanperez.jpg")
    val pfp: String?
)