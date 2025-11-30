package es.undersounds.gc01.users.repositories.specs


import es.undersounds.gc01.users.entities.Artist
import org.springframework.data.jpa.domain.Specification
import kotlin.text.lowercase

/**
 * Especificaciones para consultas dinámicas de la entidad [Artist].
 */
class ArtistSpecs {
    /**
     * Especificación para filtrar artistas cuyo nombre contiene una cadena dada (sin distinción de mayúsculas/minúsculas).
     */
    fun nameContains(name: String) = Specification<Artist> { root, _, criteriaBuilder ->
        criteriaBuilder.like(criteriaBuilder.lower(root["firstName"]), "%${name.lowercase()}%")
    }
}
