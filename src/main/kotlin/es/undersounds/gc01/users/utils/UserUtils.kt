package es.undersounds.gc01.users.utils

import es.undersounds.gc01.users.entities.User
import jakarta.annotation.PostConstruct
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import org.apache.commons.io.FilenameUtils.getExtension
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Service
object UserUtils {
    private lateinit var userPfpDir: String

    fun init(value: String) {
        userPfpDir = value
    }

    fun createUserPfpPath(user: User): Path {
        val absolutePfpDir = File(userPfpDir).absolutePath
        return Paths.get(absolutePfpDir, user.pfp)
    }

    fun createUserPfpName(user: User, coverName: String): String {
        val userId = user.publicId ?: throw RuntimeException("User must have public ID before creating profile picture name")
        return userId.toString() + '.' + getExtension(coverName)
    }

    fun createUserPfpURL(user: User) = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/$userPfpDir/${user.pfp}")
        .toUriString()
}

@Component
class AlbumUtilsConfig(
    @param:Value("\${user.pfp-drive}") private val userPfpDir: String
) {
    @PostConstruct
    fun init() {
        UserUtils.init(userPfpDir)
    }
}
