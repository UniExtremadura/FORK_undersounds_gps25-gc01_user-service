package es.undersounds.gc01.users.dtos.artists

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "Información de un artista")
data class ArtistDTO (
    @field:Schema(description = "Identificador único del artista", example = "123e4567-e89b-12d3-a456-426614174000")
    val id: UUID,

    @field:Schema(description = "Nombre real del artista", example = "Kanye Omari West")
    val name: String,

    @field:Schema(description = "Nombre artistico del artista", example = "Kanye West")
    val artisticName: String,

    @field:Schema(description = "Nombre de usuario del artista", example = "KanyeWest")
    val username: String,

    @field:Schema(description = "Biografía del artista", example = "Rapper, productor y diseñador de moda estadounidense.")
    val bio: String,

    @field:Schema(description = "Foto de perfil del artista", example = "https://cdn.example.com/artists/kanyewest.jpg")
    val pfp: String
)