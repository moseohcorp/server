package fixture.dns

import moseohcorp.server.api.dns.dto.request.RecordCreateRequest
import moseohcorp.server.api.dns.dto.request.RecordUpdateRequest
import moseohcorp.server.domain.dns.repository.entity.Record

object RecordFixture {
    fun record(
        type: Record.Type = Record.Type.A,
        subDomain: String = "subDomain",
        target: String = "target",
    ) = Record(
        type = type,
        subDomain = subDomain,
        target = target,
    )

    fun recordCreateRequest(
        type: Record.Type = Record.Type.A,
        subDomain: String = "subDomain",
        target: String = "target",
    ) = RecordCreateRequest(
        type = type,
        subDomain = subDomain,
        target = target,
    )

    fun recordUpdateRequest(
        type: Record.Type = Record.Type.A,
        subDomain: String = "subDomain",
        target: String = "target",
    ) = RecordUpdateRequest(
        type = type,
        subDomain = subDomain,
        target = target,
    )
}