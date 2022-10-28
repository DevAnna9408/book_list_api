package kr.co.book.list.lib.file.exception

import kr.co.book.list.lib.utils.MessageUtil


/**
 * 파일 not found exception
 * FileInputStream 추출시
 *
 */
class FileNotFoundException ()
    : RuntimeException(MessageUtil.getMessage("FILE_NOT_FOUND"))  {
}
