package kr.co.book.list.lib.aws.s3.exception

import kr.co.book.list.lib.utils.MessageUtil

/**
 * s3 삭제
 */
class S3DeleteException(keyName: String, keyValue: String)
    : RuntimeException("[$keyName:$keyValue] " + MessageUtil.getMessage("S3_DELETE_FAIL"))  {
}
