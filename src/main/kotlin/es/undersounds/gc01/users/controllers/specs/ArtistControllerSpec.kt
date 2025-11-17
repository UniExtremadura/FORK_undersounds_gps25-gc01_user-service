package es.undersounds.gc01.users.controllers.specs

import es.undersounds.gc01.content.dtos.SuccessDTO
import es.undersounds.gc01.users.dtos.artists.ArtistDTO
import es.undersounds.gc01.users.security.AuthenticatedUser
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal

@Tag(name = "Artistas", description = "Operaciones relacionadas con artistas. Actualización de perfil, gestión de información bancaria, etc.")
interface ArtistControllerSpec  {

    fun postArtist(@AuthenticationPrincipal user: AuthenticatedUser): ResponseEntity<SuccessDTO<ArtistDTO>>
}