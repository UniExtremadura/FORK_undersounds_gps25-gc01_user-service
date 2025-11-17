package es.undersounds.gc01.users.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class AuthenticatedUserToken(
    private val jwt: Jwt?,
    private val principal: AuthenticatedUser?,
    authorities: Collection<GrantedAuthority>
) : AbstractAuthenticationToken(authorities) {
    init {
        isAuthenticated = true
    }

    override fun getCredentials(): Any? {
        return jwt
    }

    override fun getPrincipal(): Any? {
        return principal
    }
}