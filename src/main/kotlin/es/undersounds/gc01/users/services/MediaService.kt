package es.undersounds.gc01.users.services

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.use
import kotlin.jvm.Throws
import kotlin.text.lastIndexOf
import kotlin.text.substring

/**
 * Utilidades generales para el manejo de archivos en el sistema de archivos.
 *
 * Proporciona métodos para guardar, copiar y eliminar archivos, asegurando
 * la creación de directorios y manejando correctamente errores de E/S.
 */
object MediaService {

    /**
     * Guarda un archivo recibido vía Multipart en una ubicación específica del sistema de archivos.
     */
    @Throws(IOException::class)
    fun saveFile(file: MultipartFile, filePath: String): File {
        val destFile = File(filePath)
        val parentDir = destFile.parentFile

        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw IOException("Could not create directory: ${parentDir.absolutePath}")
        }

        file.transferTo(destFile)
        return destFile
    }

    /**
     * Copia un archivo existente a una nueva ruta, reemplazando el archivo destino si ya existe.
     */
    @Throws(IOException::class)
    fun copyFile(sourceFile: File, destinationPath: String) {
        if (!sourceFile.exists() || !sourceFile.isFile) {
            throw FileNotFoundException("Source file does not exist: ${sourceFile.absolutePath}")
        }

        val destFile = File(destinationPath)
        val parentDir = destFile.parentFile

        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw IOException("Could not create directory: ${parentDir.absolutePath}")
        }

        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
    }

    /**
     * Elimina un archivo y borra recursivamente los directorios padres si quedan vacíos.
     */
    @Throws(IOException::class)
    fun deleteFile(filePath: Path) {
        if (Files.exists(filePath)) {
            Files.delete(filePath)

            var parent = filePath.parent
            while (parent != null && Files.isDirectory(parent)) {
                Files.newDirectoryStream(parent).use { entries ->
                    if (entries.iterator().hasNext()) return
                }

                Files.delete(parent)
                parent = parent.parent
            }
        }
    }

    fun getFileExtension(fileName: String): String =
        fileName.substring(fileName.lastIndexOf('.'))
}