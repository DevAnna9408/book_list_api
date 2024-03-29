package kr.co.book.list.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

/**
 * 안드로이드 푸시알림을 위한
 * Schedule 설정
 * **/
@Configuration
class ScheduledConfig {

    private val POOL_SIZE = 10

    fun scheduler(): TaskScheduler {
        val scheduler = ThreadPoolTaskScheduler()
        scheduler.poolSize = POOL_SIZE
        return scheduler
    }

}
