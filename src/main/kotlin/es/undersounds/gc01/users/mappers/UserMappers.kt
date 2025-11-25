package es.undersounds.gc01.users.mappers

import es.undersounds.gc01.users.dtos.users.UserDTO
import es.undersounds.gc01.users.entities.User
import es.undersounds.gc01.users.utils.UserUtils.createUserPfpURL

fun User.toDTO() = UserDTO(
    firstName = this.firstName,
    lastName = this.lastName,
    username = this.username,
    bio = this.bio,
    pfp = createUserPfpURL(this)
)
