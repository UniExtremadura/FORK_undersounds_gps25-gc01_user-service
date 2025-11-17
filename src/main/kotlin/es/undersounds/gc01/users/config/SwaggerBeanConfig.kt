package es.undersounds.gc01.users.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class SwaggerBeanConfig(converter: MappingJackson2HttpMessageConverter) {
    init {
        val supported = converter.supportedMediaTypes.toMutableList()
        supported.add(MediaType("application", "octet-stream"))
        converter.supportedMediaTypes = supported
    }
}