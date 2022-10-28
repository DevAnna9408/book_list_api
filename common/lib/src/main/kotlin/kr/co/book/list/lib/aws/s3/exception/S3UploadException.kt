package kr.co.book.list.lib.aws.s3.exception

import kr.co.book.list.lib.utils.MessageUtil

/**
 * s3업로드
 */
class S3UploadException(keyName: String, keyValue: String)
    : RuntimeException("[$keyName:$keyValue] " + MessageUtil.getMessage("S3_UPLOAD_FAIL"))  {
}
