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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DNS) return false
        if (!super.equals(other)) return false

        if (domain != other.domain) return false
        if (ipAddress != other.ipAddress) return false
        if (adminEmail != other.adminEmail) return false
        if (serial != other.serial) return false
        if (records != other.records) return false
        if (refresh != other.refresh) return false
        if (retry != other.retry) return false
        if (expire != other.expire) return false
        if (minimum != other.minimum) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + domain.hashCode()
        result = 31 * result + ipAddress.hashCode()
        result = 31 * result + adminEmail.hashCode()
        result = 31 * result + serial.hashCode()
        result = 31 * result + records.hashCode()
        result = 31 * result + refresh
        result = 31 * result + retry
        result = 31 * result + expire
        result = 31 * result + minimum
        return result
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