package kr.co.book.list.lib.aws.s3.domain

class FileMeta (
    var contentType : String,
    var contentEncoding : String,
    var ETag : String,
    var lastModified : String,
    var contentLength: Long
    )
