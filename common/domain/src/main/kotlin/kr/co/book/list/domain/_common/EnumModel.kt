package kr.co.book.list.domain._common

/**
 * Enum 코드 / 한글명 / 영문명
 **/
interface EnumModel {
    fun getCode(): String
    fun getKorName(): String
    fun getEngName(): String
}
