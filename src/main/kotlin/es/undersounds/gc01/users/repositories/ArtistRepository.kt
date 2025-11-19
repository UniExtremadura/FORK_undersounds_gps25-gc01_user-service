package es.undersounds.gc01.users.repositories

import es.undersounds.gc01.users.entities.Artist
import es.undersounds.gc01.users.entities.User
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ArtistRepository : CrudRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {
    fun findArtistByUserUsername(username: String): Artist?
    fun findArtistByUserPublicId(id: UUID): Artist?
    fun findByUser(user: User): Artist?
}