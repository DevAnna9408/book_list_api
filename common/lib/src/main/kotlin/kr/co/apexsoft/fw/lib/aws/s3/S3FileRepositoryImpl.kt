package kr.co.apexsoft.fw.lib.aws.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.*
import com.amazonaws.services.s3.transfer.TransferManager
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import kr.co.apexsoft.fw.lib.aws.s3.domain.FileMeta
import kr.co.apexsoft.fw.lib.aws.s3.domain.FileWrapper
import kr.co.apexsoft.fw.lib.aws.s3.exception.S3CopyException
import kr.co.apexsoft.fw.lib.aws.s3.exception.S3DeleteException
import kr.co.apexsoft.fw.lib.aws.s3.exception.S3GetDataException
import kr.co.apexsoft.fw.lib.aws.s3.exception.S3UploadException
import kr.co.apexsoft.fw.lib.file.FileRepository
import kr.co.apexsoft.fw.lib.file.FileRequest
import kr.co.apexsoft.fw.lib.file.FileResponse
import kr.co.apexsoft.fw.lib.utils.MessageUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URL
import java.util.*

@Service
class S3FileRepositoryImpl : FileRepository {
    @Value("\${aws.s3.bucketName}")
    private val bucketName: String? = null

    @Value("\${aws.s3.URL}")
    private val s3URL: String? = null

    @Autowired
    private val amazonS3: AmazonS3? = null

    override fun upload(fileRequest: FileRequest): FileResponse {
        try {
            val request = createPutObjectRequest(fileRequest)
            val tm = createTransferManager()
            val upload = tm.upload(request)
            upload.waitForCompletion()
        } catch (e: Exception) {
            throw S3UploadException(
                "FileRequest",
                fileRequest.fileName
            )
        }
        return FileResponse(
            fileRequest.fileName, fileRequest.originalFileName, fileRequest.fileSize,
            fileRequest.fileDirPath, fileRequest.getFileKey())
    }

    private fun createTransferManager(): TransferManager {
        return TransferManagerBuilder
            .standard()
            .withS3Client(amazonS3)
            .withMultipartUploadThreshold((5 * 1024 * 1025).toLong())
            .build()
    }

    private fun createPutObjectRequest(fileRequest: FileRequest): PutObjectRequest {
        return PutObjectRequest(
            bucketName, fileRequest.getFileKey(),
            fileRequest.file
        )
            .withCannedAcl(CannedAccessControlList.Private)
            .withMetadata(createObjectMetadata(fileRequest))
    }

    private fun createObjectMetadata(fileRequest: FileRequest): ObjectMetadata {
        val meta = ObjectMetadata()
        meta.contentLength = fileRequest.fileSize
        meta.contentEncoding = "UTF-8"
        return meta
    }

    override fun delete(fileKey: String) {
        try {
            amazonS3!!.deleteObject(bucketName, fileKey)
        } catch (e: Exception) {
            throw S3DeleteException("fileKey", fileKey)
        }
    }

    override fun deleteObjects(vararg fileKeys: String) {
        try {
            amazonS3!!.deleteObjects(
                DeleteObjectsRequest(bucketName).withKeys(*fileKeys).withQuiet(false)
            )
        } catch (e: RuntimeException) {
            throw S3DeleteException(
                "fileKey", fileKeys.toString())
        }
    }

    override fun copy(sourceKey: String, destinationKey: String) { //TODO: 에디터 디벨롭 이후 메소드 수정
        try {
            val copyObjRequest =
                CopyObjectRequest(bucketName, sourceKey, bucketName, destinationKey)
            amazonS3!!.copyObject(copyObjRequest)
        } catch (e: Exception) {
            throw S3CopyException("sourceKey", sourceKey)
        }
    }

    override fun getGeneratePresignedUrl(fileKey: String): URL {
        return try {
            val expiration = Date()
            var expTimeMillis = expiration.time
            expTimeMillis += (1000 * 60 * 5).toLong() //FIXME: 5분
            expiration.time = expTimeMillis
            val generatePresignedUrlRequest = GeneratePresignedUrlRequest(bucketName, fileKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration)
            amazonS3!!.generatePresignedUrl(generatePresignedUrlRequest)
        } catch (e: Exception) {
            throw S3CopyException("fileKey", fileKey)
        }
    }

    private fun createFileMeta(`object`: Any): FileMeta {
        var fileMeta: FileMeta? = null
        if (`object` is ObjectMetadata) {
            val metadata = `object`
            fileMeta = FileMeta(metadata.contentType, metadata.contentEncoding,
                metadata.eTag,metadata.lastModified.toString() ,metadata.contentLength)
        }
        return fileMeta!!
    }

    override fun getFileWrapperFromFileRepo(fileKey: String): FileWrapper {
        var s3Object: S3Object? = null
        s3Object = try {
            amazonS3!!.getObject(
                GetObjectRequest(
                    bucketName,
                    fileKey
                )
            )
        } catch (e: AmazonS3Exception) {
            throw S3GetDataException("fileKey", fileKey)
        }
        return FileWrapper(
            s3Object!!.objectContent,
            createFileMeta(s3Object.objectMetadata)
        )
    }
}
