package es.undersounds.gc01.users.exceptions

import es.undersounds.gc01.content.dtos.ErrorDTO
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.security.authorization.AuthorizationDeniedException

/**
 * Clase encargada de manejar globalmente las excepciones lanzadas en el sistema.
 * Utiliza anotaciones de Spring para manejar excepciones específicas y devolver respuestas adecuadas
 * con los códigos HTTP correspondientes.
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    /**
     * Maneja la excepción ContentNotFound. Esta excepción ocurre cuando no se encuentra el contenido solicitado.
     * Se devuelve una respuesta con el código HTTP 404 (Not Found).
     *
     * @param ex La excepción de tipo ContentNotFound.
     * @return Un objeto ResponseEntity con un mensaje de error y el código de estado 404.
     */
    @ExceptionHandler(NotFoundException::class)
    fun handleContentNotFound(ex: NotFoundException) = ResponseEntity(
        ErrorDTO( status = HttpStatus.NOT_FOUND.value(), message = "Content not found: " + ex.message),
        HttpStatus.NOT_FOUND
    )

    /**
     * Maneja la excepción NullValueException. Esta excepción ocurre cuando un valor requerido es nulo.
     * Se devuelve una respuesta con el código HTTP 400 (Bad Request).
     *
     * @param ex La excepción de tipo NullValueException.
     * @return Una respuesta con el mensaje de error y el código de estado 400.
     */
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException) = ResponseEntity(
        ErrorDTO(status = HttpStatus.BAD_REQUEST.value(), message = "Bad request error: " + ex.message),
        HttpStatus.BAD_REQUEST
    )

    /**
     * Maneja la excepción UnauthorizedException. Esta excepción ocurre cuando el usuario no está autorizado.
     * Se devuelve una respuesta con el código HTTP 401 (Unauthorized).
     *
     * @param ex La excepción de tipo UnauthorizedException.
     * @return Una respuesta con el mensaje de error y el código de estado 401.
     */
    @ExceptionHandler(ForbiddenException::class)
    fun handleUnauthorizedException(ex: ForbiddenException) = ResponseEntity(
        ErrorDTO(status = HttpStatus.FORBIDDEN.value(), message = "Forbidden request error: " + ex.message),
        HttpStatus.FORBIDDEN
    )

    /**
     * Maneja la excepción AuthorizationDeniedException. Esta excepción ocurre cuando el acceso es denegado.
     * Se devuelve una respuesta con el código HTTP 403 (Forbidden).
     *
     * @param ex La excepción de tipo AuthorizationDeniedException.
     * @return Una respuesta con el mensaje de error y el código de estado 403.
     */
    @ExceptionHandler(AuthorizationDeniedException::class)
    fun handleAccessDeniedException(ex: AuthorizationDeniedException) = ResponseEntity(
        ErrorDTO(status = HttpStatus.FORBIDDEN.value(), message = "Forbidden request error: " + ex.message),
        HttpStatus.FORBIDDEN
    )

    /**
     * Maneja todas las excepciones generales no tratadas por otros gestores.
     * Se devuelve una respuesta con el código HTTP 500 (Internal Server Error).
     *
     * @param ex    La excepción general.
     * @return Una vista de error y el código de estado 500.
     */
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneralException(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorDTO> {
        logger.error("UNHANDED ERROR AT {}: {}", request.requestURI, ex.message)
        logger.error(ex.stackTraceToString())

        val error = ErrorDTO(status = HttpStatus.INTERNAL_SERVER_ERROR.value(), message = "Internal server error")
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}