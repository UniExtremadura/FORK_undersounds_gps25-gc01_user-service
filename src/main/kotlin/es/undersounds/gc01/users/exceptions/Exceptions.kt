package es.undersounds.gc01.users.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Excepción personalizada que representa un error de solicitud incorrecta (400 Bad Request).
 *
 * @param message Mensaje descriptivo del error.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(message: String) : RuntimeException(message)

/**
 * Excepción personalizada que representa un error de acceso no autorizado (403 Forbidden).
 *
 * @param message Mensaje descriptivo del error.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
class ForbiddenException(message: String) : RuntimeException(message)

/**
 * Excepción personalizada que representa un error de contenido no encontrado (404 Not Found).
 *
 * @param message Mensaje descriptivo del error.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(message: String) : RuntimeException(message)
