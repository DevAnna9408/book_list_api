package kr.co.book.list.lib.file

import kr.co.book.list.lib.aws.s3.domain.FileWrapper
import kr.co.book.list.lib.file.exception.FileDownException
import kr.co.book.list.lib.utils.MessageUtil
import lombok.RequiredArgsConstructor
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException
import java.io.InputStream
import kotlin.jvm.Throws

@Service
@RequiredArgsConstructor
class FileService {
    private lateinit var fileRepository: FileRepository

    @Value("\${file.baseDir}")
    private val FILE_BASE_DIR: String? = null

    fun getInputStreamFromFileRepo(filePath: String): InputStream {
        val fileWrapper: FileWrapper? = fileRepository.getFileWrapperFromFileRepo(filePath)
        return fileWrapper!!.inputStream
    }

    @Throws(IOException::class)
    fun getBytesFromFileRepo(filePath: String): ByteArray {
        val inputStream = getInputStreamFromFileRepo(filePath)
        return IOUtils.toByteArray(inputStream)
    }


    /**
     * file서버에서 files 다운로드
     *
     * @param filePaths
     * @param applNo
     * @return
     */
    fun getFileListFromFileServer(applNo: Long, filePaths: Array<String?>): List<File>? {
        val uploadedFiles: MutableList<File> = ArrayList()
        for (path in filePaths) {
            uploadedFiles.add(getFileFromFileRepo(applNo, FILE_BASE_DIR!!, path!!))
        }
        return uploadedFiles
    }

    /**
     * file서버에서 file 한건 다운로드
     *
     * @param applNo
     * @param filePaths
     * @return
     */
    fun getFileFromFileServer(applNo: Long, vararg filePaths: String?): File? {
        return getFileFromFileRepo(applNo, FILE_BASE_DIR!!, *filePaths as Array<out String>)
    }


    /**
     * target 위치파일 다운로드 후 파일생성
     *
     * @param baseDir
     * @param filePaths
     * @param applNo
     * @return
     */
    private fun getFileFromFileRepo(applNo: Long, baseDir: String, vararg filePaths: String): File {
        val downFilePath = filePaths[0]
        val reFilePath = if (filePaths.size > 1) filePaths[1] else downFilePath
        var file: File? = null
        try {
            val bytes = getBytesFromFileRepo(downFilePath)
            file = File(baseDir, reFilePath)
            FileUtils.writeByteArrayToFile(file, bytes)
        } catch (e: Exception) {
            throw FileDownException()
        }
        return file
    }
}
