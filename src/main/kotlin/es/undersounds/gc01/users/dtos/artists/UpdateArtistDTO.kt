package es.undersounds.gc01.users.dtos.artists

data class UpdateArtistDTO(
    val name: String,
    val bio: String,
    val artisticName: String,
    val iban: String
)
