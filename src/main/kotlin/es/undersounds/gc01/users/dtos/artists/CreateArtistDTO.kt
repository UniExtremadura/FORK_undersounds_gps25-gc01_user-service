package es.undersounds.gc01.users.dtos.artists

data class CreateArtistDTO(
    val username: String,
    val name: String,
    val bio: String,
    val artisticName: String,
    val iban: String,
    val password: String
)

