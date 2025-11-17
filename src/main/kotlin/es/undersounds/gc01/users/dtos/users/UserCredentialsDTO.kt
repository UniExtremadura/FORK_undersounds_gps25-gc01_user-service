package es.undersounds.gc01.users.dtos.users

data class UserCredentialsDTO (
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val refreshExpiresIn: Long
)