package es.undersounds.gc01.users.dtos.identity

data class RegistrationPayload(
    val username: String,
    val enabled: Boolean = true,
    val email: String,
    val emailVerified: Boolean = true,
    val credentials: List<Credential>
)

data class Credential(
    val type: String = "password",
    val value: String,
    val temporary: Boolean = false
)