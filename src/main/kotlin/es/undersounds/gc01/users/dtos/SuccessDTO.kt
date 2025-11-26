package es.undersounds.gc01.content.dtos

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Respuesta estándar de éxito utilizada en toda la API.")
data class SuccessDTO<T>(
    @field:Schema(
        description = "Código de estado HTTP de la respuesta",
        example = "200"
    )
    val status: Int,

    @field:Schema(
        description = "Fecha y hora en la que se generó la respuesta",
        example = "2025-11-04T10:15:30"
    )
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @field:Schema(
        description = "Indica si la operación fue exitosa",
        example = "true"
    )
    val success: Boolean = true,

    @field:Schema(
        description = "Mensaje descriptivo sobre la operación",
        example = "Operación completada con éxito"
    )
    val message: String,

    @field:Schema(
        description = "Datos devueltos por la operación, de tipo genérico"
    )
    val data: T
)