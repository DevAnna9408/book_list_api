package kr.co.book.list.lib.file

import kr.co.book.list.lib._common.ApexAssert
import kr.co.book.list.lib.aws.s3.exception.S3UploadException
import kr.co.book.list.lib.file.exception.FileConvertException
import kr.co.book.list.lib.file.exception.FileSizeException
import kr.co.book.list.lib.utils.FilePathUtil
import kr.co.book.list.lib.utils.MessageUtil
import lombok.extern.slf4j.Slf4j
import org.apache.commons.io.FilenameUtils
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest
import java.io.*
import java.util.*
import java.util.function.Supplier

@Slf4j
class FileRequest{
    lateinit var fileExt: String
    lateinit var fileName: String
    lateinit var originalFileName: String
    var fileSize: Long = 0
    lateinit var file: File
    lateinit var fileDirPath: String

    constructor (fileDirPath: String?, fileName: String?) {
        this.file = File(fileDirPath, fileName)
        this.originalFileName = fileName!!
        this.fileName = fileName
        this.fileDirPath = fileDirPath!!
        this.fileSize = this.file.length()
    }

    constructor (file: File, fileDirPath: String, fileName: String?) {
        this.file = file
        this.originalFileName = fileName!!
        this.fileName = fileName
        this.fileDirPath = fileDirPath!!
        this.fileSize = file.length()
    }

    constructor (
        file: File,
        fileDirPath: String?,
        fileName: String?,
        originalFileName: String?
    ) {
        this.file = file
        this.originalFileName = originalFileName!!
        this.fileName = fileName!!
        this.fileDirPath = fileDirPath!!
        this.fileSize = file.length()
    }

    constructor (
        multipartReq: StandardMultipartHttpServletRequest?,
        maxImgSize: String,
        maxPdfSize: String
    ) : this(){
        val fileMap = multipartReq!!.fileMap
        val entrySet: Set<Map.Entry<String, MultipartFile>> = fileMap.entries
        ApexAssert.isTrue(
            entrySet.size == 1, MessageUtil.getMessage("FILE_CONVERT_FAIL"),
            S3UploadException::class.java
        )
        val (_, multipartFile) = entrySet.iterator().next()
        this.originalFileName = FilePathUtil.removeSeparators(multipartFile.originalFilename.toString())
        this.file = convert(multipartFile).orElseThrow(Supplier<RuntimeException> {
            FileConvertException()
        })!!
        this.fileName = this.originalFileName // 따로 지정하지 않는경우, 원본파일명으로 됨
        this.fileExt = FilenameUtils.getExtension(this.originalFileName)
        this.fileSize = checkFileSize(multipartFile.size, maxImgSize, maxPdfSize)!!
    }

    constructor()


    /**
     * 모든 파일 객체에 대해서 사이즈 검사
     * @param fileSize
     * @param maxImgSize
     * @param maxPdfSize
     * @return
     */
    private fun checkFileSize(fileSize: Long, maxImgSize: String, maxPdfSize: String): Long? {
        val isPdf = fileExt.equals("pdf", ignoreCase = true)
        val maxSize = if (isPdf) maxPdfSize else maxImgSize
        val msg = if (isPdf) "MAX_PDF_SIZE_FAIL" else "MAX_IMAGE_SIZE_FAIL"
        if (fileSize > maxSize.toDouble() * 1024 * 1024) {
            throw FileSizeException(MessageUtil.getMessage(msg, arrayOf(maxSize)))
        }
        return fileSize
    }

    private fun convert(file: MultipartFile): Optional<File> {
        var convertFile: File? = null
        convertFile = try {
            File.createTempFile("upload", "temp")
        } catch (e: IOException) {
            return Optional.empty()
        } finally {
            convertFile!!.deleteOnExit()
        }
        try {
            file.transferTo(convertFile!!)
        } catch (e: IOException) {
            return Optional.empty()
        }
        return Optional.of(convertFile)
    }

    fun getFileInputStream(): InputStream? {
        return try {
            FileInputStream(file)
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException()
        }
    }

    fun setCustomFileDirPath(fileDirPath: String?) {
        this.fileDirPath = fileDirPath!!
    }

    fun setCustomFileName(fileName: String?) {
        this.fileName = fileName!!
    }

    fun getFileKey(): String {
        return fileDirPath + "/" + fileName
    }
}
