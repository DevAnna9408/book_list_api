package kr.co.apexsoft.fw.domain.model.log

import kr.co.apexsoft.fw.domain._common.AbstractEntity
import javax.persistence.*

@Entity
@Table( name = "QUERY_LOG")
class QueryLog(
    oid: Long? = null,

    @Column(name="URL")
    val url: String,

    @Column(name="USER_ID")
    val userId: String,

    @Column(name="ACTION")
    @Enumerated(EnumType.STRING)
    val action: Action,
) : AbstractEntity(oid) {
    enum class Action {
        QUERY, DOWNLOAD
    }
}
