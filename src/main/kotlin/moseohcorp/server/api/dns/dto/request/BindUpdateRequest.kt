package moseohcorp.server.api.dns.dto.request

import jakarta.validation.constraints.NotBlank

data class BindUpdateRequest(
    @field:NotBlank
    val domain: String,

    @field:NotBlank
    val ipAddress: String,
)