package es.undersounds.gc01.users.mappers

import es.undersounds.gc01.users.dtos.users.UserDTO
import es.undersounds.gc01.users.entities.User

fun User.toDTO() = UserDTO(
    firstName = this.firstName,
    lastName = this.lastName,
    username = this.username,
    bio = this.bio,
    pfp = this.pfp
)
