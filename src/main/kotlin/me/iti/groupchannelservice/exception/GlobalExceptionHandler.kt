package me.iti.groupchannelservice.exception

import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebExchange

@RestControllerAdvice
class GlobalExceptionHandler {
    val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler
    fun handleException(
        ex: Exception,
        request: ServerWebExchange
    ): ResponseEntity<ApiError> {
        log.error("Internal error", ex)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiError(Errors.INTERNAL_ERROR, Errors.INTERNAL_ERROR.message))
    }

    @ExceptionHandler
    fun handleNotFoundException(
        ex: NotFoundException,
        request: ServerWebExchange
    ): ResponseEntity<ApiError> {
        log.error("NotFoundException", ex)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiError(ex.error.code, ex.error.message))
    }

    @ExceptionHandler
    fun handleNotUniqueException(
        ex: NotUniqueException,
        request: ServerWebExchange
    ): ResponseEntity<ApiError> {
        log.error("NotUniqueException", ex)
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiError(ex.error.code, ex.error.message))
    }

    @ExceptionHandler
    fun handleCustomException(
        ex: CustomException,
        request: ServerWebExchange
    ): ResponseEntity<ApiError> {
        log.error("Exception", ex)
        return ResponseEntity
            .status(CUSTOM_STATUS_CODE)
            .body(ApiError(ex.error.code, ex.error.message))
    }

    companion object {
        private const val CUSTOM_STATUS_CODE = 499
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException): ResponseEntity<ApiError> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiError(code = Errors.ENTITY_NOT_FOUND.name, message = ex.message))
    }

    @ExceptionHandler(EntityAlreadyExistsException::class)
    fun handleEntityAlreadyExistsException(ex: EntityAlreadyExistsException): ResponseEntity<ApiError> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiError(ex.error.code, ex.error.message))
    }

}
