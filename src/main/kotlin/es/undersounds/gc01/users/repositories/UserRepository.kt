package es.undersounds.gc01.users.repositories

import es.undersounds.gc01.users.entities.User
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
    fun findUserByPublicId(publicId: UUID): User?

    fun findUserByUsername(username: String): User?
}