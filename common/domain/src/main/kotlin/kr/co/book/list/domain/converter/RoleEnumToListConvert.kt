package kr.co.book.list.domain.converter

import kr.co.book.list.domain._common.EnumModel
import kr.co.book.list.domain.model.Role
import javax.persistence.Convert

/**
 * role list num 변환
 */
@Convert
class RoleEnumToListConvert(private val targetEnumClass: Class<out EnumModel> = Role::class.java) :
    EnumToListConverter(targetEnumClass) {
}
