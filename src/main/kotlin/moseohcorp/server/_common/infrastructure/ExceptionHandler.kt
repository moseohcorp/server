package moseohcorp.server._common.infrastructure

import moseohcorp.server._common.dto.ApiResponse
import moseohcorp.server._common.exception.ApiException
import moseohcorp.server._common.exception.ErrorCode
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ExceptionHandler {
//    private val logger = logger()

    // Api Exception
    @ExceptionHandler(ApiException::class)
    fun handleApiException(e: ApiException): ResponseEntity<ApiResponse> {
        return ApiResponse.ofFailure(e.errorCode)
    }

    // Request Dto 양식이 올바르지 않는 경우 발생
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ApiResponse> {
        return ApiResponse.ofFailure(ErrorCode.BAD_REQUEST)
    }

    // dto validation
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse> {
        fun MethodArgumentNotValidException.messages(): String {
            return bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage.orEmpty()}" }
        }
        return ApiResponse.ofFailure(HttpStatus.BAD_REQUEST, e.messages())
    }

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun handleBadRequestException(e: RuntimeException): ResponseEntity<ApiResponse> {
        return ApiResponse.ofFailure(HttpStatus.BAD_REQUEST, e.message)
    }

    @Order(Int.MAX_VALUE)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse> {
//        logger.error("unhandled exception: {}", e.message)
        return ApiResponse.ofFailure(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }
}