package kr.co.apexsoft.fw.domain.converter

import kr.co.apexsoft.fw.domain._common.EnumModel
import kr.co.apexsoft.fw.domain.model.Role
import javax.persistence.Convert

/**
 * role list num 변환
 */
@Convert
class RoleEnumToListConvert(private val targetEnumClass: Class<out EnumModel> = Role::class.java) :
    EnumToListConverter(targetEnumClass) {
}
