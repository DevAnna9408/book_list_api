package kr.co.book.list.domain.exception

import kr.co.book.list.domain._common.AbstractEntity
import kotlin.reflect.KClass

class DomainEntityNotFoundException(
    val id: Any,
    val entityClazz: KClass<out AbstractEntity>,
    msg: String,
) : RuntimeException(msg) {
}
