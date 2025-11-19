package es.undersounds.gc01.users.dtos.identity

import com.fasterxml.jackson.annotation.JsonProperty

data class ServiceCredentialsDTO(
    @field:JsonProperty("access_token")
    val accessToken: String,

    @field:JsonProperty("expires_in")
    val expiresIn: Long,

    @field:JsonProperty("refresh_expires_in")
    val refreshExpiresIn: Long
)
