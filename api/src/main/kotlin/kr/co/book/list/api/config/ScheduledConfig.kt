package kr.co.book.list.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class ScheduledConfig {

    private val POOL_SIZE = 10

    fun scheduler(): TaskScheduler {
        val scheduler = ThreadPoolTaskScheduler()
        scheduler.poolSize = POOL_SIZE
        return scheduler
    }

}
