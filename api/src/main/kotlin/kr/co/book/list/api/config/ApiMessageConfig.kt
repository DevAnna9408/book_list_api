package kr.co.book.list.api.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource

/**
 * api 메세지 설정
 * 다국어 설정 시 다국어 처리도 가능
 **/
@Configuration
class ApiMessageConfig {

    // Locale에 대한 메세지를 로드
    @Autowired
    var messageSource: ReloadableResourceBundleMessageSource? = null

    //
    @Bean
    fun addMessageBaseName() {
        messageSource!!.addBasenames("classpath:/api-messages/message")
    }

}
