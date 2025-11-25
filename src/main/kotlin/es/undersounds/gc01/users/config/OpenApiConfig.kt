package es.undersounds.gc01.users.config

import es.undersounds.gc01.users.dtos.ErrorDTO
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig{
    @Value("\${openapi.server.url}")
    private lateinit var serverUrl: String

    @Bean
    fun serverCustomizer(): OpenApiCustomizer {
        return OpenApiCustomizer { openApi ->
            openApi.servers.clear()
            openApi.addServersItem(Server().url(serverUrl))
        }
    }

    @Bean
    fun addJwtSecurity(): OpenApiCustomizer = OpenApiCustomizer { openApi ->
        openApi.paths.forEach { (path, pathItem) ->
            if(!path.contains("public")){
                pathItem.readOperations().forEach { operation ->
                    operation.addSecurityItem(SecurityRequirement().addList("bearerAuth"))
                    operation.responses.addApiResponse(
                        "403",
                        ApiResponse()
                            .description("Usuario no autorizado")
                            .content(
                                Content().addMediaType(
                                    "application/json",
                                    MediaType().schema(
                                        Schema<ErrorDTO>()
                                            .`$ref`("#/components/schemas/ErrorDTO")
                                            .example("{\n" +
                                                    "  \"status\": 403,\n" +
                                                    "  \"timestamp\": \"2025-11-04T10:15:30\",\n" +
                                                    "  \"success\": false,\n" +
                                                    "  \"message\": \"Acceso denegado\"\n" +
                                                    "}")
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}