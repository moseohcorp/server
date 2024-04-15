package fixture.dns

import moseohcorp.server.api.dns.dto.request.BindCreateRequest
import moseohcorp.server.api.dns.dto.request.BindUpdateRequest
import moseohcorp.server.domain.dns.repository.entity.Bind

object BindFixture {
    fun bind(
        domain: String = "domain",
        ipAddress: String = "123.123.123.123",
    ) = Bind(
        domain = domain,
        ipAddress = ipAddress
    )

    fun bindCreateRequest(
        domain: String = "domain",
        ipAddress: String = "123.123.123.123",
    ) = BindCreateRequest(
        domain = domain,
        ipAddress = ipAddress
    )

    fun bindUpdateRequest(
        domain: String = "domain",
        ipAddress: String = "123.123.123.123",
    ) = BindUpdateRequest(
        domain = domain,
        ipAddress = ipAddress
    )
}