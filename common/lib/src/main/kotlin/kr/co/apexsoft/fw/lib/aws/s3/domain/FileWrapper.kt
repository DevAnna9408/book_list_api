package kr.co.apexsoft.fw.lib.aws.s3.domain

import java.io.InputStream

class FileWrapper(
    var inputStream: InputStream,
    var fileMeta: FileMeta
    )
