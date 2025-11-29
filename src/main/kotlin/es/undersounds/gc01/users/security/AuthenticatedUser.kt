package es.undersounds.gc01.users.security

import org.springframework.security.oauth2.jwt.Jwt
import java.util.UUID

data class AuthenticatedUser(
    val id: UUID,
    val username: String,
    val email: String,
    val roles: MutableSet<String> = mutableSetOf()
) {
    constructor(jwt: Jwt) : this(
        id = UUID.fromString(jwt.getClaimAsString("sub")),
        username = jwt.getClaimAsString("username"),
        email = jwt.getClaimAsString("email") ?: "",
        roles = jwt.getClaim<List<String>?>("roles")?.toMutableSet() ?: mutableSetOf()
    )
}