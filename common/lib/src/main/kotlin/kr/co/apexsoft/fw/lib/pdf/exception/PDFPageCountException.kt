package kr.co.apexsoft.fw.lib.pdf.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil

class PDFPageCountException (val fileName : String)
    : RuntimeException("[$fileName] "+ MessageUtil.getMessage("PDF_COUNT_FAIL")) {
}
