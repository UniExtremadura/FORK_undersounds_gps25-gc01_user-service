package es.undersounds.gc01.users.dtos.users

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Payload para actualizar los datos personales de un usuario. No incluye el identificador: éste se extrae del JWT.")
data class UpdateUserDTO(
    @field:Schema(description = "Nombre real del usuario", example = "Juan Pérez")
    val name: String,

    @field:Schema(description = "Biografía del usuario", example = "Desarrollador y amante de la música.")
    val bio: String
)
