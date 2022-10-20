package kr.co.apexsoft.fw.lib.pdf.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil

class PDFPageInsertException (val filePath : String)
    : RuntimeException("[$filePath] "+ MessageUtil.getMessage("PDF_PAGE_INSERT_FAIL")) {
}
