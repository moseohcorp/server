package moseohcorp.server.api.dns.dto.request

import jakarta.validation.constraints.NotEmpty
import moseohcorp.server.domain.dns.repository.entity.Record

data class RecordUpdateRequest(
    @field:NotEmpty
    val type: Record.Type,

    @field:NotEmpty
    val subDomain: String,

    @field:NotEmpty
    val target: String,
)