package moseohcorp.server.domain.dns.repository.entity

import jakarta.persistence.*
import moseohcorp.server._common.entity.BaseAuditingEntity
import moseohcorp.server.api.dns.dto.request.BindCreateRequest
import moseohcorp.server.api.dns.dto.request.BindUpdateRequest

@Entity
class Bind(
    specificDomain: String,
    ipAddress: String,
) : BaseAuditingEntity() {
    @Column(nullable = false)
    var specificDomain: String = specificDomain
        private set

    @Column(nullable = false)
    var ipAddress: String = ipAddress
        private set

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name="bind_id")
    var portForward: PortForward? = null
        private set

    fun add(portForward: PortForward) {
        this.portForward = portForward
    }

    fun update(request: BindUpdateRequest) {
        this.specificDomain = request.specificDomain
        this.ipAddress = request.ipAddress
    }

    fun removePortForward() {
        this.portForward = null
    }

    companion object {
        fun of(request: BindCreateRequest) = Bind(
            specificDomain = request.specificDomain,
            ipAddress = request.ipAddress
        )
    }
}