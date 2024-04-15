package moseohcorp.server._common.entity

import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseTimeEntity : BaseEntity() {
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    var modifiedAt: LocalDateTime = LocalDateTime.now()
        protected set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseTimeEntity) return false
        if (!super.equals(other)) return false

        if (createdAt != other.createdAt) return false
        if (modifiedAt != other.modifiedAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + modifiedAt.hashCode()
        return result
    }


}