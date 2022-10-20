package kr.co.apexsoft.fw.lib.aws.s3.exception

/**
 * s3 공용 익셉션
 */
class S3CommException(keyName: String, keyValue: String, warningMessage: String)
    : RuntimeException("[$keyName:$keyValue]$warningMessage")  {
}
