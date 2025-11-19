package es.undersounds.gc01.users.services

import es.undersounds.gc01.users.clients.IdentityClient
import es.undersounds.gc01.users.dtos.artists.ArtistDTO
import es.undersounds.gc01.users.dtos.artists.ArtistFilters
import es.undersounds.gc01.users.dtos.artists.CreateArtistDTO
import es.undersounds.gc01.users.dtos.artists.UpdateArtistDTO
import es.undersounds.gc01.users.entities.Artist
import es.undersounds.gc01.users.exceptions.BadRequestException
import es.undersounds.gc01.users.exceptions.ForbiddenException
import es.undersounds.gc01.users.exceptions.NotFoundException
import es.undersounds.gc01.users.mappers.toDTO
import es.undersounds.gc01.users.mappers.toSpecification
import es.undersounds.gc01.users.repositories.ArtistRepository
import es.undersounds.gc01.users.repositories.UserRepository
import es.undersounds.gc01.users.security.AuthenticatedUser
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ArtistService(
    private val userService: UserService,
    private val identityClient: IdentityClient,
    private val userRepository: UserRepository,
    private val artistRepository: ArtistRepository
) {
    @Transactional
    fun postArtist(user: AuthenticatedUser, artistInfo: CreateArtistDTO): ArtistDTO{
        val userEntity = userRepository.findUserByPublicId(user.id)
            ?: throw ForbiddenException("No estás autorizado a crear una cuenta de artista")

        val artistEntity = artistRepository.findByUser(userEntity)

        if(artistEntity != null) throw BadRequestException("Ya eres un artista")

        require(userEntity.publicId != null) { "User $userEntity must have public ID" }
        require(userEntity.publicId != null) { "User $userEntity must have public ID" }

        val artist = Artist(
            user = userEntity,
            artisticName = artistInfo.artisticName,
            iban = artistInfo.iban
        )

        val saved = artistRepository.save(artist)
        identityClient.giveRolArtistToUser(user.id)
        val dto = saved.toDTO()

        return dto
    }

    fun getArtistByUsername(username: String): ArtistDTO {
        val artist = artistRepository.findArtistByUserUsername(username)
            ?: throw NotFoundException("No se encontró a ningun artista con el username: $username")

        return artist.toDTO()
    }

    fun getArtistsFiltered(
        artistFilters: ArtistFilters,
        page: Int,
        size: Int
    ): Page<ArtistDTO> {
        val spec = artistFilters.toSpecification()

        val pageable = PageRequest.of(page, size)
        val pageResult = artistRepository.findAll(spec, pageable)

        return pageResult.map {artist -> artist.toDTO()}
    }

    fun updateArtist(user: AuthenticatedUser, artistUpdate: UpdateArtistDTO, pfp: MultipartFile?): ArtistDTO {
        val artist = artistRepository.findArtistByUserPublicId(user.id)
            ?: throw NotFoundException("No se ha encontrado ningun artista con el UD ${user.id}")

        userService.updateUser(user, artistUpdate, pfp)

        artistUpdate.artisticName?.let { artist.artisticName = it }
        artistUpdate.iban?.let { artist.iban = it }

        artistRepository.save(artist)

        return artist.toDTO()
    }

    @Transactional
    fun deleteArtist(user: AuthenticatedUser) {
        if(user.roles.contains("artist")) {
            throw ForbiddenException("No tienes un perfil de artista")
        }

        val artist = artistRepository.findArtistByUserPublicId(user.id)
            ?: throw NotFoundException("No se ha encontrado ningun artista con ID ${user.id}")

        identityClient.unableUser(user.id)
        artist.deleted = true
        artistRepository.save(artist)
    }
}