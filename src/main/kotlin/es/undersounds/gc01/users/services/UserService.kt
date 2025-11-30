package es.undersounds.gc01.users.services

import es.undersounds.gc01.users.clients.IdentityClient
import es.undersounds.gc01.users.dtos.users.CreateUserDTO
import es.undersounds.gc01.users.dtos.users.UserDTO
import es.undersounds.gc01.users.dtos.users.UpdateUserDTO
import es.undersounds.gc01.users.entities.User
import es.undersounds.gc01.users.exceptions.ForbiddenException
import es.undersounds.gc01.users.exceptions.NotFoundException
import es.undersounds.gc01.users.mappers.toDTO
import es.undersounds.gc01.users.repositories.UserRepository
import es.undersounds.gc01.users.security.AuthenticatedUser
import es.undersounds.gc01.users.security.JwtToAuthenticatedUserConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.stereotype.Service
import es.undersounds.gc01.users.utils.logger
import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UserService(
    private val identityClient: IdentityClient,
    private val userMediaService: UserMediaService,
    private val userRepository: UserRepository
) {
    private val logger = logger<UserService>()

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private lateinit var issuerUri: String

    private lateinit var decoder: JwtDecoder
    private lateinit var converter: JwtToAuthenticatedUserConverter

    @PostConstruct
    private fun init() {
        decoder = JwtDecoders.fromIssuerLocation(issuerUri)
        converter = JwtToAuthenticatedUserConverter()
    }

    @Transactional
    fun postUser(user: AuthenticatedUser, dto: CreateUserDTO, pfp: MultipartFile): UserDTO {
        val prev = userRepository.findUserByPublicId(user.id)

        if(prev != null){
            throw ForbiddenException("Ya has completado tu perfil")
        }

        val userEntity = User(
            publicId = user.id,
            username = user.username,
            firstName = dto.firstName,
            email = user.email,
            lastName = dto.lastName,
            bio = dto.bio,
        )

        try {
            userMediaService.savePfp(userEntity, pfp)
            val saved = userRepository.save(userEntity)

            logger.info("User registered with ID: ${userEntity.publicId} and username: ${userEntity.username}")

            return saved.toDTO()
        } catch (e: Exception) {
            userMediaService.deletePfp(userEntity)

            throw e
        }
    }


    fun getUser(user: AuthenticatedUser): UserDTO {
        val user = userRepository.findUserByPublicId(user.id)
            ?: throw NotFoundException("No se encontró a ningun usuario con el ID: ${user.id}")

        return user.toDTO()
    }

    fun getUserByUsername(username: String): UserDTO {
        val user = userRepository.findUserByUsername(username)
            ?: throw NotFoundException("No se encontró a ningun usuario con el username: $username")

        return user.toDTO()
    }

    @Transactional
    fun updateUser(user: AuthenticatedUser, userInfo: UpdateUserDTO, pfp: MultipartFile?): UserDTO {
        val user = userRepository.findUserByPublicId(user.id)
            ?: throw NotFoundException("No se encontró a ningun usuario con el ID: ${user.id}")

        userInfo.firstName?.let { user.firstName = it }
        userInfo.lastName?.let { user.lastName = it }
        userInfo.bio?.let { user.bio = it }

        userMediaService.updatePfp(user, pfp)

        val updated = userRepository.save(user)

        return updated.toDTO()
    }

    @Transactional
    fun deleteUser(user: AuthenticatedUser) {
        if(user.roles.contains("artist")){
            throw ForbiddenException("Los artistas no pueden borrar la información de su perfil")
        }

        val user = userRepository.findUserByPublicId(user.id)
            ?: throw NotFoundException("No se encontró a ningun usuario con el ID: ${user.id}")

        userMediaService.deletePfp(user)
        userRepository.delete(user)
    }
}
