package moseohcorp.server._common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {
    /**
     * COMMON
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "올바른 요청형식이 아닙니다."),

    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다."),

    /**
     * DNS
     */
    DOMAIN_NOT_FOUND(HttpStatus.NOT_FOUND, "Domain not found."),
    RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "Record not found."),
    BIND_NOT_FOUND(HttpStatus.NOT_FOUND, "Bind not found."),
    PORT_FORWARD_NOT_FOUND(HttpStatus.NOT_FOUND, "Port forward not found."),
}