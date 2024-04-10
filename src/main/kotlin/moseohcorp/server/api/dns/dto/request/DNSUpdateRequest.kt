package moseohcorp.server.api.dns.dto.request

import jakarta.validation.constraints.NotBlank

data class DNSUpdateRequest(
    @field:NotBlank
    val domain: String,

    @field:NotBlank
    val ipAddress: String,

    @field:NotBlank
    val adminEmail: String,
)