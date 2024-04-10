package moseohcorp.server.api.dns.dto.response

import moseohcorp.server.domain.dns.repository.entity.Record

data class RecordSummaryResponse(
    val type: Record.Type,
    val subDomain: String,
    val target:String,
)