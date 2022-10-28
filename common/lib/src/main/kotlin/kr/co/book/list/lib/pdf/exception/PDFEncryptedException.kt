package kr.co.book.list.lib.pdf.exception

import kr.co.book.list.lib.utils.MessageUtil

class PDFEncryptedException (val fileName : String)
    : RuntimeException("[$fileName] "+ MessageUtil.getMessage("ENCRYPTED_PDF_FILE_FAIL")) {
}
