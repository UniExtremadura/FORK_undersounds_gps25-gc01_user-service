package es.undersounds.gc01.content.dtos

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Respuesta de error estándar utilizada en toda la API.")
data class ErrorDTO(
    @field:Schema(
        description = "Código de estado HTTP del error",
        example = "404"
    )
    val status: Int,

    @field:Schema(
        description = "Fecha y hora en la que ocurrió el error",
        example = "2025-11-04T10:15:30"
    )
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @field:Schema(
        description = "Indica si la operación fue exitosa (siempre false en caso de error)",
        example = "false"
    )
    val success: Boolean = false,

    @field:Schema(
        description = "Mensaje descriptivo del error",
        example = "Recurso no encontrado"
    )
    val message: String
)