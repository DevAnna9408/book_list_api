package kr.co.book.list.lib.file

import kr.co.book.list.lib.file.exception.FileByteConvertException
import kr.co.book.list.lib.utils.MessageUtil
import org.springframework.core.io.InputStreamResource
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.util.*
import javax.servlet.http.HttpServletRequest

object FileUtils {
    /**
     * request에서 multipart 파일 1개 추출
     * @param request
     * @return
     */
    fun getFileRequest(
        request: HttpServletRequest,
        maxImgSize: String,
        maxPdfSize: String
    ): FileRequest? {
        var multipartReq: StandardMultipartHttpServletRequest? = null
        if (StandardMultipartHttpServletRequest::class.java.isAssignableFrom(request.javaClass)) {
            multipartReq = request as StandardMultipartHttpServletRequest
        }
        Objects.requireNonNull(multipartReq)
        return FileRequest(multipartReq, maxImgSize, maxPdfSize)
    }

    fun retrieveInputStreamResource(file: File?): InputStreamResource? {
        return try {
            InputStreamResource(
                ByteArrayInputStream(
                    org.apache.commons.io.FileUtils.readFileToByteArray(
                        file
                    )
                )
            )
        } catch (e: IOException) {
            throw FileByteConvertException()
        }
    }

    fun retrieveInputStreamResource(byteArrayInputStream: ByteArrayInputStream?): InputStreamResource? {
        return InputStreamResource(byteArrayInputStream!!)
    }
}
