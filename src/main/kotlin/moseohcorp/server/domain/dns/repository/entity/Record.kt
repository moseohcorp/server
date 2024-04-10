package moseohcorp.server.domain.dns.repository.entity

import jakarta.persistence.*
import moseohcorp.server._common.entity.BaseAuditingEntity
import moseohcorp.server.api.dns.dto.request.RecordCreateRequest
import moseohcorp.server.api.dns.dto.request.RecordUpdateRequest

@Entity
class Record(
    type: Type,
    subDomain: String,
    target: String,
) : BaseAuditingEntity() {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: Type = type
        private set

    @Column(nullable = false)
    var subDomain: String = subDomain
        private set

    @Column(nullable = false)
    var target: String = target
        private set

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name="record_id")
    var portForward: PortForward? = null
        private set

    enum class Type {
        A,
        CNAME
    }

    fun update(portForward: PortForward) {
        this.portForward = portForward
    }

    fun update(request: RecordUpdateRequest) {
        this.type = request.type
        this.subDomain = request.subDomain
        this.target = request.target
    }

    fun removePortForward() {
        this.portForward = null
    }

    companion object {
        fun of (request: RecordCreateRequest) = Record(
            type = request.type,
            subDomain = request.subDomain,
            target = request.target
        )
    }
}