package es.undersounds.gc01.users.entities

import jakarta.persistence.*
import java.util.UUID

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
class User(
    @Column(unique = true, nullable = false, updatable = false)
    var publicId: UUID? = null,

    @Column(unique = true, nullable = false)
    var username: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, length = 1000)
    var bio: String,

    @Column
    var pfp: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    var id: Long? = null
}