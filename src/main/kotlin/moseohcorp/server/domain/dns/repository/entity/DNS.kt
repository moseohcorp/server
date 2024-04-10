package moseohcorp.server.domain.dns.repository.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import moseohcorp.server._common.entity.BaseAuditingEntity
import moseohcorp.server.api.dns.dto.request.DNSCreateRequest
import moseohcorp.server.api.dns.dto.request.DNSUpdateRequest
import java.text.SimpleDateFormat
import java.util.Date

@Entity
class DNS(
    domain: String,
    ipAddress: String,
    adminEmail: String,
) : BaseAuditingEntity() {
    @Column(nullable = false)
    var domain: String = domain
        private set

    @Column(nullable = false)
    var ipAddress: String = ipAddress
        private set

    @Column(nullable = false)
    var adminEmail: String = adminEmail
        private set

    @Column(nullable = false)
    val serial:String = generateSerial()

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "dns_id")
    val records: MutableList<Record> = mutableListOf()

    val refresh: Int = 7200
    val retry: Int = 3600
    val expire: Int = 1209600
    val minimum: Int = 3600

    fun add(record: Record) {
        this.records.add(record)
    }

    fun update(request: DNSUpdateRequest) {
        this.domain = request.domain
        this.ipAddress = request.ipAddress
        this.adminEmail = request.adminEmail
    }

    companion object {
        fun of(request: DNSCreateRequest) = DNS(
            domain = request.domain,
            ipAddress = request.ipAddress,
            adminEmail = request.adminEmail,
        )

        private val DATE_FORMAT = SimpleDateFormat("yyyyMMddss")
        private fun generateSerial(): String {
            return DATE_FORMAT.format(Date())
        }
    }
}