package io.github.codenilson.lavava2026.infra

import io.github.codenilson.lavava2026.application.exceptions.InvalidCredentialsException
import io.github.codenilson.lavava2026.application.exceptions.ResourceAlreadyExists
import io.github.codenilson.lavava2026.application.exceptions.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalExceptionHandler {
    data class ErrorResponse(
        val status: Int,
        val error: String,
        val message: String?,
        val path: String?
    )

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(ex: InvalidCredentialsException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.UNAUTHORIZED
        val error = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = ex.message,
            path = request.getDescription(false).substringAfter("uri=")
        )
        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(ResourceAlreadyExists::class)
    fun handleResourceAlreadyExists(ex: ResourceAlreadyExists, request: WebRequest): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.CONFLICT // 409
        val error = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = ex.message,
            path = request.getDescription(false).substringAfter("uri=")
        )
        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.NOT_FOUND
        val error = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = ex.message,
            path = request.getDescription(false).substringAfter("uri=")
        )
        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR // 500
        val error = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = "An unexpected error occurred.",
            path = request.getDescription(false).substringAfter("uri=")
        )

        return ResponseEntity.status(status).body(error)
    }
}