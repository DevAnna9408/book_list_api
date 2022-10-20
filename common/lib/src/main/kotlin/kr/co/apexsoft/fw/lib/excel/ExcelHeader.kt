package kr.co.apexsoft.fw.lib.excel

import org.apache.poi.ss.usermodel.IndexedColors

data class ExcelHeader(val style: List<HeaderStyle>) {

    constructor(vararg data: HeaderStyle) : this(data.toList())
    constructor(vararg data: String) : this(data.map { HeaderStyle(data = it) })

    data class HeaderStyle(
        val data: String,
        val bold: Boolean = true,
        val fontSize: Short = 15.toShort(),
        val backColor: Short = IndexedColors.GREY_25_PERCENT.index,
        val fontColor: Short = IndexedColors.BLACK.index
    ) {
    }

    fun size(): Int {
        return style.size
    }

}
