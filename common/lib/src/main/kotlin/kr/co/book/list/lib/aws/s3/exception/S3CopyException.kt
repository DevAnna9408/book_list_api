package kr.co.book.list.lib.aws.s3.exception

import kr.co.book.list.lib.utils.MessageUtil

/**
 * s3 객체간 복사
 */
class S3CopyException(keyName: String, keyValue: String)
    : RuntimeException("[$keyName:$keyValue] " +  MessageUtil.getMessage("S3_COPY_FAIL"))  {
}
