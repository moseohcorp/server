package fixture.dns

import moseohcorp.server.api.dns.dto.request.BindCreateRequest
import moseohcorp.server.api.dns.dto.request.BindUpdateRequest
import moseohcorp.server.domain.dns.repository.entity.Bind

object BindFixture {
    fun bind(
        specificDomain: String = "domain",
        ipAddress: String = "123.123.123.123",
    ) = Bind(
        specificDomain = specificDomain,
        ipAddress = ipAddress
    )

    fun bindCreateRequest(
        specificDomain: String = "domain",
        ipAddress: String = "123.123.123.123",
    ) = BindCreateRequest(
        specificDomain = specificDomain,
        ipAddress = ipAddress
    )

    fun bindUpdateRequest(
        specificDomain: String = "domain",
        ipAddress: String = "123.123.123.123",
    ) = BindUpdateRequest(
        specificDomain = specificDomain,
        ipAddress = ipAddress
    )
}