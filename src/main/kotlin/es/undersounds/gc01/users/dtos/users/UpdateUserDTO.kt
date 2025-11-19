package es.undersounds.gc01.users.dtos.users

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Payload para actualizar los datos personales de un usuario. No incluye el identificador: éste se extrae del JWT.")
open class UpdateUserDTO(
    @field:Schema(description = "Nombre real del usuario", example = "Juan")
    val firstName: String?,

    @field:Schema(description = "Apellidos reales del usuario", example = "Pérez")
    val lastName: String?,

    @field:Schema(description = "Biografía del usuario", example = "Desarrollador y amante de la música.")
    val bio: String?
)
