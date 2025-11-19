package es.undersounds.gc01.users.dtos.artists

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Información para crear un artista")
data class CreateArtistDTO(
    @field:Schema(description = "Nombre artístico", example = "Kanye West")
    val artisticName: String,

    @field:Schema(description = "IBAN del artista", example = "ES9121000418450200051332")
    val iban: String,
)

