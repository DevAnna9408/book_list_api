package kr.co.book.list.lib.aws.s3.exception

import kr.co.book.list.lib.utils.MessageUtil

/**
 * s3 데이터 다운로드
 */
class S3GetDataException(keyName: String, keyValue: String)
    : RuntimeException("[$keyName:$keyValue] " + MessageUtil.getMessage("S3_DOWN_FAIL"))  {
}
