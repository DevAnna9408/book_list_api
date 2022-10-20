package kr.co.apexsoft.fw.domain.exception

import kr.co.apexsoft.fw.domain._common.AbstractEntity
import kotlin.reflect.KClass

class DomainEntityNotFoundException(
    val id: Any,
    val entityClazz: KClass<out AbstractEntity>,
    msg: String,
) : RuntimeException(msg) {
}
