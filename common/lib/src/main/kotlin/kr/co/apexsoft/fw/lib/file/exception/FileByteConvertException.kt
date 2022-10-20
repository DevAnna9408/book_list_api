package kr.co.apexsoft.fw.lib.file.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil

/**
 * 파일 Byte[] 컨버트
 */
class FileByteConvertException ()
    : RuntimeException(MessageUtil.getMessage("FILE_TO_BYTE_CONVERT_FAIL"))  {
}
