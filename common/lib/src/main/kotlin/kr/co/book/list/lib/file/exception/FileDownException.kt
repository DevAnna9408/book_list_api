package kr.co.book.list.lib.file.exception

import kr.co.book.list.lib.utils.MessageUtil


class FileDownException ()
    : RuntimeException(MessageUtil.getMessage("FILE_DOWN_FAIL"))  {
}
