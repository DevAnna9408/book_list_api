package kr.co.book.list.lib.pdf

data class PDFPageInfo(val name :String) {




    fun getLeftTop(): String {
        //TODO: 좌측상단 텍스트 필요하면 수정
        return "좌측상단 $name"
    }

}
