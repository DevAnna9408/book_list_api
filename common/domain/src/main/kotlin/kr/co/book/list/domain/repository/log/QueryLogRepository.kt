package kr.co.book.list.domain.repository.log

import kr.co.book.list.domain.model.log.QueryLog
import org.springframework.data.jpa.repository.JpaRepository

interface QueryLogRepository : JpaRepository<QueryLog, Long> {
}
