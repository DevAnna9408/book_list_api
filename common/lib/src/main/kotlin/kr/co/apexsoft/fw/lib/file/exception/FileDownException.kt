package kr.co.apexsoft.fw.lib.file.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil


class FileDownException ()
    : RuntimeException(MessageUtil.getMessage("FILE_DOWN_FAIL"))  {
}
