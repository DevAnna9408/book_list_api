package kr.co.apexsoft.fw.lib.file.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil

/**
 * MultipartFile -> file 전환
 */
class FileConvertException ()
    : RuntimeException(MessageUtil.getMessage("MULTI_FILE_TO_FILE_CONVERT_FAIL"))  {
}
