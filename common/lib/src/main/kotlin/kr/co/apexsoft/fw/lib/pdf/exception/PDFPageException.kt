package kr.co.apexsoft.fw.lib.pdf.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil

class PDFPageException (val file : String, val warningMessage: String)
    : RuntimeException("[$file] $warningMessage" ) {
}
