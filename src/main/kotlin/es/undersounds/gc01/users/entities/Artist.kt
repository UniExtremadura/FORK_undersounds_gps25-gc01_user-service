package es.undersounds.gc01.users.entities

import jakarta.persistence.*
import java.util.UUID

class Artist(
    publicId: UUID,
    username: String,
    name: String,
    bio: String,

    @Column(nullable = false)
    var artisticName: String,

    @Column(nullable = false)
    var  iban: String
) : User( publicId, username, name, bio)