package kr.co.apexsoft.fw.lib.file

import kr.co.apexsoft.fw.lib.aws.s3.domain.FileWrapper
import org.springframework.stereotype.Repository
import java.net.URL

interface FileRepository {

    fun upload(fileRequest: FileRequest): FileResponse

    fun delete(fileKey: String)

    fun deleteObjects(vararg fileKeys: String)

    fun copy(sourceKey: String, destinationKey: String)

    fun getGeneratePresignedUrl(fileKey: String): URL

    fun getFileWrapperFromFileRepo(fileKey: String): FileWrapper
}
