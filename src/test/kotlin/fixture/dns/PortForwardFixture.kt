package fixture.dns

import moseohcorp.server.api.dns.dto.request.PortForwardCreateRequest
import moseohcorp.server.api.dns.dto.request.PortForwardUpdateRequest
import moseohcorp.server.domain.dns.repository.entity.PortForward

object PortForwardFixture {
    fun portForward(
        port: Int = 1234
    ) = PortForward(
        port = port
    )

    fun portForwardCreateRequest(
        port: Int = 1234
    ) = PortForwardCreateRequest(
        port = port
    )

    fun portForwardUpdateRequest(
        port: Int = 1234
    ) = PortForwardUpdateRequest(
        port = port
    )
}