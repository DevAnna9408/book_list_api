package kr.co.book.list.lib.pdf.exception

import kr.co.book.list.lib.utils.MessageUtil

class PDFPageInsertException (val filePath : String)
    : RuntimeException("[$filePath] "+ MessageUtil.getMessage("PDF_PAGE_INSERT_FAIL")) {
}