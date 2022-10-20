package kr.co.apexsoft.fw.lib.aws.s3.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil

/**
 * s3 객체간 복사
 */
class S3CopyException(keyName: String, keyValue: String)
    : RuntimeException("[$keyName:$keyValue] " +  MessageUtil.getMessage("S3_COPY_FAIL"))  {
}
