package kr.co.book.list.domain.model.log

import kr.co.book.list.domain._common.AbstractEntity
import javax.persistence.*

@Entity
@Table( name = "B_QUERY_LOG")
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
