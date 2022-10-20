package kr.co.apexsoft.fw.lib.excel


data class ExcelSheet(
    val sheetName: String,
    val headers: ExcelHeader,
    val rows: List<ExcelRow>
) {

   fun headersData(): List<String> {
        return this.headers.style.map { it.data }
    }
}
