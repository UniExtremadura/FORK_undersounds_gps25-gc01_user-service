package es.undersounds.gc01.users.security

import org.springframework.security.oauth2.jwt.Jwt
import java.util.UUID

data class AuthenticatedUser(
    val username: String,
    val id: UUID,
    val roles: MutableSet<String> = mutableSetOf()
) {
    constructor(jwt: Jwt) : this(
        username = jwt.getClaimAsString("username"),
        id = UUID.fromString(jwt.getClaimAsString("sub")),
        roles = jwt.getClaim<List<String>?>("roles")?.toMutableSet() ?: mutableSetOf()
    )
}