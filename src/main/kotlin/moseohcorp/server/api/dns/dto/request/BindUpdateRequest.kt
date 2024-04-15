package moseohcorp.server.api.dns.dto.request

import jakarta.validation.constraints.NotBlank

data class BindUpdateRequest(
    @field:NotBlank
    val specificDomain: String,

    @field:NotBlank
    val ipAddress: String,
)