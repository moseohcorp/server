package moseohcorp.server._common.entity

import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy

@MappedSuperclass
abstract class BaseAuditingEntity : BaseTimeEntity() {
    @CreatedBy
    var createdBy: Long = 0L
        protected set

    @LastModifiedBy
    var modifiedBy: Long = 0L
        protected set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseAuditingEntity) return false
        if (!super.equals(other)) return false

        if (createdBy != other.createdBy) return false
        if (modifiedBy != other.modifiedBy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + createdBy.hashCode()
        result = 31 * result + modifiedBy.hashCode()
        return result
    }

}