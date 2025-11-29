package es.undersounds.gc01.users.controllers

import es.undersounds.gc01.content.dtos.SuccessDTO
import es.undersounds.gc01.users.services.ArtistService
import es.undersounds.gc01.users.controllers.specs.ArtistControllerSpec
import es.undersounds.gc01.users.dtos.artists.ArtistDTO
import es.undersounds.gc01.users.dtos.artists.ArtistFilters
import es.undersounds.gc01.users.dtos.artists.CreateArtistDTO
import es.undersounds.gc01.users.dtos.artists.UpdateArtistDTO
import es.undersounds.gc01.users.security.AuthenticatedUser
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("artists")
class ArtistController (private val artistService: ArtistService) : ArtistControllerSpec {
    override fun postArtist(
        user: AuthenticatedUser,
        artistInfo: CreateArtistDTO
    ): ResponseEntity<SuccessDTO<ArtistDTO>> {
        val artist = artistService.postArtist(user, artistInfo)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.CREATED.value(),
                message = "Artista creado correctamente",
                data = artist
            ),
            HttpStatus.CREATED
        )
    }

    override fun getArtist(user: AuthenticatedUser): ResponseEntity<SuccessDTO<ArtistDTO>> {
        val artist = artistService.getArtist(user)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.OK.value(),
                message = "Artista obtenido correctamente",
                data = artist
            ),
            HttpStatus.OK
        )
    }

    override fun getArtistById(id: UUID): ResponseEntity<SuccessDTO<ArtistDTO>> {
        val artist = artistService.getArtistById(id)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.OK.value(),
                message = "Artista obtenido correctamente",
                data = artist
            ),
            HttpStatus.OK
        )
    }

    override fun getArtistByUsername(username: String): ResponseEntity<SuccessDTO<ArtistDTO>> {
        val artist = artistService.getArtistByUsername(username)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.OK.value(),
                message = "Artista obtenido correctamente",
                data = artist
            ),
            HttpStatus.OK
        )
    }

    override fun getArtistsFiltered(
        artistFilters: ArtistFilters,
        page: Int,
        size: Int
    ): ResponseEntity<SuccessDTO<Page<ArtistDTO>>> {
        val artists = artistService.getArtistsFiltered(artistFilters, page, size)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.OK.value(),
                message = "Artistas obtenidos correctamente",
                data = artists
            ),
            HttpStatus.OK
        )
    }

    override fun updateArtist(
        user: AuthenticatedUser,
        artistUpdate: UpdateArtistDTO,
        pfp: MultipartFile?
    ): ResponseEntity<SuccessDTO<ArtistDTO>> {
        val artist = artistService.updateArtist(user, artistUpdate, pfp)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.OK.value(),
                message = "Artista actualizado correctamente",
                data = artist
            ),
            HttpStatus.OK
        )
    }

    override fun deleteArtist(user: AuthenticatedUser): ResponseEntity<SuccessDTO<Unit>> {
        artistService.deleteArtist(user)
        return ResponseEntity(
            SuccessDTO(
                status = HttpStatus.OK.value(),
                message = "Artista borrado correctamente",
                data = Unit
            ),
            HttpStatus.OK
        )
    }
}