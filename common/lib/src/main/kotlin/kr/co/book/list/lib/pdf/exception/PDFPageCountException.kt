package kr.co.book.list.lib.pdf.exception

import kr.co.book.list.lib.utils.MessageUtil

class PDFPageCountException (val fileName : String)
    : RuntimeException("[$fileName] "+ MessageUtil.getMessage("PDF_COUNT_FAIL")) {
}
