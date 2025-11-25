package es.undersounds.gc01.users.controllers.specs

import es.undersounds.gc01.users.dtos.ErrorDTO
import es.undersounds.gc01.users.dtos.SuccessDTO
import es.undersounds.gc01.users.dtos.artists.ArtistDTO
import es.undersounds.gc01.users.dtos.artists.ArtistFilters
import es.undersounds.gc01.users.dtos.artists.CreateArtistDTO
import es.undersounds.gc01.users.dtos.artists.UpdateArtistDTO
import es.undersounds.gc01.users.dtos.users.UserCredentialsDTO
import es.undersounds.gc01.users.security.AuthenticatedUser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Artistas", description = "Operaciones relacionadas con artistas. Actualización de perfil, gestión de información bancaria, etc.")
interface ArtistControllerSpec  {
    @Operation(
        summary = "Crea un nuevo artista",
        description = "Permite a un usuario autenticado elevar su categoría a artista. La información del usuario se inyecta en el JWT. El usuario NO puede tener rol de artista para acceder a este endpoint"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Artista creado correctamente"),
            ApiResponse(
                responseCode = "400",
                description = "Error en los datos enviados",
                content = [Content(schema = Schema(implementation = ErrorDTO::class))]
            )
        ]
    )
    @PreAuthorize("!hasRole('artist')")
    @PostMapping
    fun postArtist(
        @AuthenticationPrincipal user: AuthenticatedUser,
        @Parameter(description = "Información del artista adicional necesaria para crear su perfil") @RequestBody @Valid artistInfo: CreateArtistDTO
    ): ResponseEntity<SuccessDTO<ArtistDTO>>

    @Operation(
        summary = "Obtiene la información de un artista",
        description = "Obtiene información pública de un artista en base a su username"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Artista obtenido correctamente"),
            ApiResponse(
                responseCode = "400",
                description = "Error en los datos enviados",
                content = [Content(schema = Schema(implementation = ErrorDTO::class))]
            )
        ]
    )
    @GetMapping("/public/{username}")
    @PreAuthorize("hasRole('internal_service')")
    fun getArtistByUsername(
        @Parameter(description = "Nombre de usuario del artista a obtener", example = "JohnLennon")
        @PathVariable username: String
    ): ResponseEntity<SuccessDTO<ArtistDTO>>

    @Operation(
        summary = "Obtiene una página de artistas",
        description = "Obtiene una página con información completa de artistas en base a filtros"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Artistas obtenidos correctamente"),
            ApiResponse(responseCode = "400", description = "Error en los datos enviados", content = [Content(schema = Schema(implementation = ErrorDTO::class))])
        ]
    )
    @GetMapping("/public")
    fun getArtistsFiltered(
        @ParameterObject @ModelAttribute @Valid
        artistFilters: ArtistFilters,

        @Parameter(description = "Número de página (empezando en 0)", example = "0")
        @RequestParam("page") page: Int = 0,

        @Parameter(description = "Cantidad de elementos por página", example = "20")
        @RequestParam("size") size: Int = 20
    ): ResponseEntity<SuccessDTO<Page<ArtistDTO>>>

    @Operation(
        summary = "Actualiza un artista",
        description = "Actualiza un artista con nueva información como su nombre artístico o IBAN"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Artista actualizado correctamente"),
            ApiResponse(responseCode = "400", description = "Error en los datos enviados", content = [Content(schema = Schema(implementation = ErrorDTO::class))])
        ]
    )
    @PutMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateArtist(
        @AuthenticationPrincipal user: AuthenticatedUser,

        @Parameter(description = "Información del artista para actualizar su perfil")
        @RequestPart("update-artist-info") @Valid artistUpdate: UpdateArtistDTO,

        @Parameter(description = "Foto de perfil nueva para el artista")
        @RequestPart("profile-picture") pfp: MultipartFile?
    ): ResponseEntity<SuccessDTO<ArtistDTO>>

    @Operation(
        summary = "Inhabilita un artista",
        description = "Inhabilita el perfil de un artista pero NO borra su información. Cuando creas un perfil de artista con UnderSounds aceptas que tu información se mantenga en la plataforma para poder proveerla con el contenido que los usuarios compran." +
                " Tras inhabilitar un artista no podrás acceder a tu cuenta."
    )
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Artistas obtenidos correctamente")])
    @PreAuthorize("hasRole('artist')")
    @DeleteMapping
    fun deleteArtist(@AuthenticationPrincipal user: AuthenticatedUser): ResponseEntity<SuccessDTO<Unit>>
}