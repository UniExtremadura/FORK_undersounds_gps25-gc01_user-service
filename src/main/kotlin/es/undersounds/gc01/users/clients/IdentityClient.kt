package es.undersounds.gc01.users.clients

import es.undersounds.gc01.users.dtos.users.UserCredentialsDTO

class IdentityClient {



    fun login(username: String, password: String): UserCredentialsDTO? {
        // Lógica para llamar al servicio de identidad y obtener el token
        return null
    }

    fun register(username: String, password: String, name: String): UserCredentialsDTO? {
        // Lógica para llamar al servicio de identidad y registrar un nuevo usuario
        return null
    }
}