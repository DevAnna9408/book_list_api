package kr.co.book.list.domain._common

import org.springframework.context.support.MessageSourceAccessor

/**
 * Domain 디렉토리에서 사용할 메세지 설정
 **/
object DomainMessageUtil {
    var messageSourceAccessorDomain: MessageSourceAccessor? = null

    fun getMessage(key: String?): String {
        println(messageSourceAccessorDomain)
        return messageSourceAccessorDomain!!.getMessage(key!!)
    }

    fun getMessage(key: String?, objs: Array<Any?>?): String {
        return messageSourceAccessorDomain!!.getMessage(key!!, objs)
    }
}
