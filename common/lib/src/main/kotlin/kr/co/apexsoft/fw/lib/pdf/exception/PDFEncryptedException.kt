package kr.co.apexsoft.fw.lib.pdf.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil

class PDFEncryptedException (val fileName : String)
    : RuntimeException("[$fileName] "+ MessageUtil.getMessage("ENCRYPTED_PDF_FILE_FAIL")) {
}
