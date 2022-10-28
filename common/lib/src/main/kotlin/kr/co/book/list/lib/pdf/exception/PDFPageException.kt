package kr.co.book.list.lib.pdf.exception

import kr.co.book.list.lib.utils.MessageUtil

class PDFPageException (val file : String, val warningMessage: String)
    : RuntimeException("[$file] $warningMessage" ) {
}
