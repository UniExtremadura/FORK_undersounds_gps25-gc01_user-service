package es.undersounds.gc01.users.dtos.artists

import es.undersounds.gc01.users.dtos.users.UpdateUserDTO

open class UpdateArtistDTO(
    firstName: String?,
    lastName: String?,
    bio: String?,
    val artisticName: String?,
    val iban: String?
): UpdateUserDTO(firstName, lastName, bio)
