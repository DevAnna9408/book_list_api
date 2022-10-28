package kr.co.book.list.lib.file.exception

import kr.co.book.list.lib.utils.MessageUtil

/**
 * MultipartFile -> file 전환
 */
class FileConvertException ()
    : RuntimeException(MessageUtil.getMessage("MULTI_FILE_TO_FILE_CONVERT_FAIL"))  {
}
