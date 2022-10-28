package kr.co.book.list.lib.aws.s3.domain

import java.io.InputStream

class FileWrapper(
    var inputStream: InputStream,
    var fileMeta: FileMeta
    )
