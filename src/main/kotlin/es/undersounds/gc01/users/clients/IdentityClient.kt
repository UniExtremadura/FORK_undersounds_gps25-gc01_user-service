package es.undersounds.gc01.users.clients

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

import es.undersounds.gc01.users.dtos.identity.ServiceCredentialsDTO
import es.undersounds.gc01.users.dtos.users.CreateUserDTO
import es.undersounds.gc01.users.dtos.identity.Credential
import es.undersounds.gc01.users.dtos.identity.RegistrationPayload
import es.undersounds.gc01.users.dtos.users.UserCredentialsDTO
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import java.util.UUID

@Service
class IdentityClient {
    @Value("\${service.id}")
    private lateinit var clientId: String

    @Value("\${service.secret}")
    private lateinit var clientSecret: String

    @Value("\${keycloak.url}")
    private lateinit var baseUrl: String

    @Value("\${keycloak.realm}")
    private lateinit var realm: String

    private lateinit var serviceToken: ServiceCredentialsDTO

    @PostConstruct
    private fun init() {
        serviceToken = getServiceToken()
    }

    private fun getServiceToken(): ServiceCredentialsDTO {
        val formData = LinkedMultiValueMap<String, String>()
        formData.add("grant_type", "client_credentials")
        formData.add("client_id", clientId)
        formData.add("client_secret", clientSecret)

        val credentials = WebClient.create("$baseUrl/realms/$realm/protocol/openid-connect/token")
            .post()
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(ServiceCredentialsDTO::class.java)
            .block()

        if (credentials == null) throw RuntimeException("Unable to get user service token. Credentials = $credentials")

        return credentials
    }

    fun login(username: String, password: String): UserCredentialsDTO? {
        val formData = LinkedMultiValueMap<String, String>()
        formData.add("client_id", clientId)
        formData.add("client_secret", clientSecret)
        formData.add("grant_type", "password")
        formData.add("username", username)
        formData.add("password", password)

        return WebClient.create("$baseUrl/realms/$realm/protocol/openid-connect/token")
            .post()
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(UserCredentialsDTO::class.java)
            .block()
    }

    fun register(user: CreateUserDTO): UserCredentialsDTO? {
        val registrationPayload = RegistrationPayload(
            username = user.username,
            email = user.email,
            credentials = listOf(Credential(value = user.password))
        )

        serviceToken = getServiceToken()
        WebClient.create("$baseUrl/admin/realms/$realm/users")
            .post()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer ${serviceToken.accessToken}")
            .bodyValue(registrationPayload)
            .retrieve()
            .toBodilessEntity()
            .block()

        return login(user.username, user.password)
    }

    fun giveRolArtistToUser(userId: UUID) {
        serviceToken = getServiceToken()
        WebClient.create("$baseUrl/admin/realms/$realm/users/$userId/role-mappings/realm")
            .post()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer ${serviceToken.accessToken}")
            .bodyValue(arrayOf(mapOf("id" to "06d61af5-f8dc-49d2-8c7d-6551061c5ddc", "name" to "artist")))
            .retrieve()
            .toBodilessEntity()
            .block()
    }

    fun unableUser(userId: UUID){
        serviceToken = getServiceToken()
        WebClient.create("$baseUrl/admin/realms/$realm/users/$userId")
            .put()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer ${serviceToken.accessToken}")
            .bodyValue(mapOf("enabled" to false))
            .retrieve()
            .toBodilessEntity()
            .block()
    }

    fun deleteUser(userId: UUID) {
        serviceToken = getServiceToken()
        WebClient.create("$baseUrl/admin/realms/$realm/users/$userId")
            .delete()
            .header("Authorization", "Bearer ${serviceToken.accessToken}")
            .retrieve()
            .toBodilessEntity()
            .block()
    }
}