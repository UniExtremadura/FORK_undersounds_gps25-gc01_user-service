package es.undersounds.gc01.users.clients

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import es.undersounds.gc01.users.dtos.identity.ServiceCredentialsDTO
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

    // CORRECCIÓN CLAVE 1: La propiedad serviceToken usa el bloque 'lazy'
    private val serviceToken: ServiceCredentialsDTO by lazy { 
        // Llama a la función renombrada para evitar colisión de firma Java.
        fetchServiceToken() 
    }

    // CORRECCIÓN CLAVE 2: La función que calcula el token ha sido renombrada
    // de getServiceToken() a fetchServiceToken().
    private fun fetchServiceToken(): ServiceCredentialsDTO {
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

    fun giveRolArtistToUser(userId: UUID) {
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
        WebClient.create("$baseUrl/admin/realms/$realm/users/$userId")
            .delete()
            .header("Authorization", "Bearer ${serviceToken.accessToken}")
            .retrieve()
            .toBodilessEntity()
            .block()
    }
}
