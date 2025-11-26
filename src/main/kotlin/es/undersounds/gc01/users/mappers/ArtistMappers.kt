package es.undersounds.gc01.users.mappers

import es.undersounds.gc01.users.dtos.artists.ArtistDTO
import es.undersounds.gc01.users.dtos.artists.ArtistFilters
import es.undersounds.gc01.users.entities.Artist
import es.undersounds.gc01.users.repositories.specs.ArtistSpecs
import org.springframework.data.jpa.domain.Specification

fun Artist.toDTO(): ArtistDTO {
    return ArtistDTO(
        id = this.user.publicId!!,
        firstName = this.user.firstName,
        lastName = this.user.lastName,
        artisticName = this.artisticName,
        username = this.user.username,
        bio = this.user.bio,
        pfp = this.user.pfp!!
    )
}

fun ArtistFilters.toSpecification(): Specification<Artist>? {
    val artistSpecs = ArtistSpecs()
    val specs = mutableListOf<Specification<Artist>>()

    name?.let { specs += artistSpecs.nameContains(it) }

    return specs.reduceOrNull(Specification<Artist>::and)
}