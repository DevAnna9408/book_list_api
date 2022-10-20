package kr.co.apexsoft.fw.lib.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object FilePathUtil {
    private const val SLASH_WINDOW = "/"
    private const val SLASH_UNIX = "\\"
    private const val DOUBLE_SLASH = "//"
    private const val ENCODED_AMP = "&amp;"
    private const val RECOVERED_AMP = "&"
    private const val ENCODED_SINGLEQUOTE = "&#39;"
    private const val RECOVERED_SINGLEQUOTE = "'"

    /**
     * 버트로 만든 파일 만들어지는 경로
     *
     * @param baseDir
     * @param schoolCode
     * @param enterYear
     * @param recruitPartSeq
     * @param applNo
     * @return
     */
    fun getMakeDirectoryFullPath(
        baseDir: String?,
        schoolCode: String?, enterYear: String?, recruitPartSeq: Int?,
        applNo: Long
    ): String {
        return StringBuilder()
            .append(baseDir).append("/")
            .append(schoolCode).append("/")
            .append(enterYear).append("/")
            .append(recruitPartSeq).append("/")
            .append(applNo.toString())
            .toString()
    }

    fun removeSeparators(rawOriginalFileName: String): String {
        var index: Int =
            rawOriginalFileName.lastIndexOf(SLASH_UNIX)
        val tmpFileName = rawOriginalFileName.substring(index + 1, rawOriginalFileName.length)
        index =
            tmpFileName.lastIndexOf(SLASH_WINDOW)
        return tmpFileName.substring(index + 1, tmpFileName.length)
    }

    /**
     * localdatetime 을 시분초 String 으로 반환
     * 파일경로에 자주 사용되는 패턴
     *
     * @return
     */
    fun getUploadDateTime(): String? {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
    }

    fun recoverAmpersand(path: String): String {
        return path.replace(
            ENCODED_AMP,
            RECOVERED_AMP
        )
            .replace(
                ENCODED_AMP,
                RECOVERED_AMP
            )
            .replace(
                ENCODED_AMP,
                RECOVERED_AMP
            )
    }

    fun recoverSingleQuote(path: String): String {
        return path.replace(
            ENCODED_SINGLEQUOTE,
            RECOVERED_SINGLEQUOTE
        )
    }
}
