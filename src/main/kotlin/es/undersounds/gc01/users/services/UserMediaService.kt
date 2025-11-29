package es.undersounds.gc01.users.services

import es.undersounds.gc01.users.entities.User
import es.undersounds.gc01.users.exceptions.BadRequestException
import es.undersounds.gc01.users.services.MediaService.deleteFile
import es.undersounds.gc01.users.services.MediaService.saveFile
import es.undersounds.gc01.users.utils.UserUtils.createUserPfpName
import es.undersounds.gc01.users.utils.UserUtils.createUserPfpPath
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import kotlin.text.startsWith

@Service
class UserMediaService {
    @Value("\${users.max-pfp-size}")
    private lateinit var maxPfpSize: Integer

    fun savePfp(
        user: User,
        pfp: MultipartFile
    ) {
        if (pfp.size > maxPfpSize.toInt()) {
            throw BadRequestException("Profile picture file exceeds the maximum limit")
        }

        if (pfp.contentType?.startsWith("image/") != true)
            throw BadRequestException("Invalid file type for profile picture. Only images are allowed.")

        if(pfp.originalFilename == null)
            throw BadRequestException("Profile picture must have a name")

        val extension = pfp.contentType?.substringAfterLast('/')?.lowercase()
            ?: throw BadRequestException("Profile picture must have a valid extension")

        val pfpName = createUserPfpName(user, extension)

        user.pfp = pfpName
        val pfpPath: Path = createUserPfpPath(user)
        saveFile(pfp, pfpPath.toString())
    }

    fun updatePfp(user: User, pfp: MultipartFile?){
        if (pfp != null && !pfp.isEmpty && (pfp.contentType?.startsWith("image/") != true))
            throw BadRequestException("Invalid file type for cover. Only images are allowed.")


        if (pfp != null && !pfp.isEmpty) {
            if (pfp.size > maxPfpSize.toInt()) throw BadRequestException("Profile picture file exceeds the maximum limit")
            if (pfp.originalFilename == null) throw BadRequestException("Profile picture must have a name")

            val pfpPath = createUserPfpPath(user)
            deleteFile(pfpPath)
            savePfp(user, pfp)
        }
    }

    fun deletePfp(user: User){
        val pfpPath = createUserPfpPath(user)
        deleteFile(pfpPath)
        user.pfp = null
    }
}