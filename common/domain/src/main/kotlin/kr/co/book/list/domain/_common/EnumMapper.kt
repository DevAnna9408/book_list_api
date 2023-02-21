package kr.co.book.list.domain._common


/**
 * Enum으로 등록된 Enum 목록을
 * 이름으로 추가하고 목록으로 조회
 * **/
class EnumMapper {
    private val factory: MutableMap<String, List<EnumValue>> = HashMap<String, List<EnumValue>>()

    private fun toEnumValues(e: Class<out EnumModel>): List<EnumValue> {
        return e.enumConstants.map { EnumValue(it) }
    }

    fun put(key: String, e: Class<out EnumModel>) {
        factory[key] = toEnumValues(e)
    }

    operator fun get(keys: String): Map<String, List<EnumValue>?> {
        return keys.split(",").toTypedArray().associateWith { key: String -> factory[key] }

    }
}
