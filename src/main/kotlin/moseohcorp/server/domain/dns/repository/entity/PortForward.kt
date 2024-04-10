package moseohcorp.server.domain.dns.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import moseohcorp.server._common.entity.BaseAuditingEntity
import moseohcorp.server.api.dns.dto.request.PortForwardCreateRequest
import moseohcorp.server.api.dns.dto.request.PortForwardUpdateRequest

@Entity
class PortForward(
    port: Int,
) : BaseAuditingEntity() {
    @Column(nullable = false)
    var port: Int = port
        private set

    fun update(request: PortForwardUpdateRequest) {
        this.port = request.port
    }

    companion object {
        fun of(request: PortForwardCreateRequest) = PortForward(
            port = request.port
        )
    }
}