package kr.co.book.list.lib.file

import kr.co.book.list.lib.aws.s3.domain.FileMeta
import java.io.InputStream
import java.nio.charset.Charset
import kotlin.experimental.and

class FileDto(
    var inputStream: InputStream,
    var fileMeta: FileMeta
) {
    private fun encodeStrToUTF8(str: String): String? {
        val sb = StringBuilder()
        val btArray = str.toByteArray(Charset.forName("UTF-8"))
        var i = 0
        while (i < btArray.size) {
            val c = (btArray[i].toShort() and 0x00ff) as Short
            if (c == '%'.toShort() || c == ' '.toShort() || c == ' '.toShort()) {
                sb.append(String.format("%%%02x", c))
            } else if (c < 128) {
                sb.append(String.format("%c", c))
            } else {
                if (i + 2 < btArray.size) {
                    sb.append(
                        String.format(
                            "%%%02X%%%02X%%%02X",
                            btArray[i], btArray[i + 1], btArray[i + 2]
                        )
                    )
                    i += 2
                }
            }
            i++
        }
        return sb.toString()
    }
}
