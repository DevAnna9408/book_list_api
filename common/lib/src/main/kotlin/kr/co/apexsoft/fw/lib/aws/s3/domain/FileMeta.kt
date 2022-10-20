package kr.co.apexsoft.fw.lib.aws.s3.domain

class FileMeta (
    var contentType : String,
    var contentEncoding : String,
    var ETag : String,
    var lastModified : String,
    var contentLength: Long
    )
