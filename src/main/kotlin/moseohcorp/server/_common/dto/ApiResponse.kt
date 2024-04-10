package moseohcorp.server._common.dto

import moseohcorp.server._common.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ApiResponse(
    val data: Any? = null,
    val message: String? = null,
    val error: Boolean = false,
) {
    companion object {
        /*
         * success
         */
        fun of(
            data: Any? = null,
            message: String? = null,
            status: HttpStatus = HttpStatus.OK,
        ): ResponseEntity<ApiResponse> = ResponseEntity(
            ApiResponse(
                data = data,
                message = message,
                error = false,
            ), status
        )

        /*
         * failure
         */
        fun ofFailure(
            errorCode: ErrorCode,
        ): ResponseEntity<ApiResponse> = ResponseEntity(
            ApiResponse(
                data = null,
                message = errorCode.message,
                error = true,
            ), errorCode.status
        )

        fun ofFailure(
            status: HttpStatus,
            message: String?
        ): ResponseEntity<ApiResponse> = ResponseEntity(
            ApiResponse(
                data = null,
                message = message,
                error = true,
            ), status
        )

    }
}