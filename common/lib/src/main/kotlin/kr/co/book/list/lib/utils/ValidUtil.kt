package kr.co.book.list.lib.utils

import org.springframework.util.StringUtils

object ValidUtil {
    private const val NUMBER_ALPHABET_PATTERN = "^[a-zA-Z\\d]+$"
    private const val NUMERIC_PATTERN = "^[\\+\\-]{0,1}[\\d]+$"
    private const val FLOAT_PATTERN = "^[\\+\\-]{0,1}[\\d]+[\\.][0-9]+$"
    private const val MAIL_PATTERN = "^[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+$"

    private fun ValidUtil() {}

    /**
     * 값이 영문 알파벳, 숫자가 아닌 경우
     */
    fun isAlphaNumber(`object`: String): Boolean {
        return isMatched(`object`, NUMBER_ALPHABET_PATTERN)
    }


    /**
     * * 값이 숫자가 아닌 경우
     */
    fun isNumeric(`object`: String): Boolean {
        return isMatched(`object`, NUMERIC_PATTERN)
    }


    /**
     * 값이 float, double이 아닌 경우
     */
    fun isFloat(`object`: String): Boolean {
        return isMatched(`object`, FLOAT_PATTERN)
    }


    fun isMail(`object`: String): Boolean {
        return isMatched(`object`, MAIL_PATTERN)
    }

    /**
     * 패턴 매치, 해당 패턴과 일치 하지 않는 경우
     *
     * @param value
     */
    private fun isMatched(`object`: String, pattern: String): Boolean {
        return StringUtils.hasText(`object`) && `object`.matches(pattern.toRegex())
    }

}
