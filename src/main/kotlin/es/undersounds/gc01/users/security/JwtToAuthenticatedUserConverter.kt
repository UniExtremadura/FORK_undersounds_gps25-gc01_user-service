package es.undersounds.gc01.users.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class JwtToAuthenticatedUserConverter : Converter<Jwt?, AuthenticatedUserToken?> {
    override fun convert(jwt: Jwt): AuthenticatedUserToken {
        val user = AuthenticatedUser(jwt)
        val authorities = user.roles.map { role -> SimpleGrantedAuthority("ROLE_$role") }
        return AuthenticatedUserToken(jwt, user, authorities)
    }
}