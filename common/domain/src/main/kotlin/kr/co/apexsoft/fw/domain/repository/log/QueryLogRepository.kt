package kr.co.apexsoft.fw.domain.repository.log

import kr.co.apexsoft.fw.domain.model.log.QueryLog
import org.springframework.data.jpa.repository.JpaRepository

interface QueryLogRepository : JpaRepository<QueryLog, Long> {
}
