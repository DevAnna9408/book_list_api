package kr.co.apexsoft.fw.lib.excel


import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.xssf.streaming.SXSSFSheet
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream


private const val HEADER_ROW = 0

@Component
class ExcelGenerator {

    /**
     * 엑셀 만들기
     */
    fun dataToExcel(sheets: List<ExcelSheet>): ByteArrayInputStream {
        val workbook = SXSSFWorkbook()
        for (sheet: ExcelSheet in sheets) {
            setExcelData(sheet, workbook)
        }
        val out = ByteArrayOutputStream()
        workbook.write(out)
        workbook.close()

        return ByteArrayInputStream(out.toByteArray())
    }

    /**
     * 엑셀 데이터추출
     */
    fun excelToData(inputStream: InputStream): List<ExcelSheet> {
        val data: MutableList<ExcelSheet> = ArrayList()
        val workbook = XSSFWorkbook(inputStream)
        val sheetCount = workbook.numberOfSheets - 1
        for (i: Int in 0..sheetCount) {
            val sheet = workbook.getSheetAt(i)
            data.add(getExcelData(sheet))
        }
        return data
    }

    private fun setExcelData(excelSheet: ExcelSheet, workbook: SXSSFWorkbook) {
        val sheet = workbook.createSheet(excelSheet.sheetName)
        styleHeaders(workbook, sheet as SXSSFSheet, excelSheet.headers.style)
        fillData(sheet, excelSheet.rows, excelSheet.headers.size())
    }

    /**
     * 헤더 스타일 &데이터 세팅
     */
    private fun styleHeaders(workbook: SXSSFWorkbook, sheet: SXSSFSheet, excelHeader: List<ExcelHeader.HeaderStyle>) {
        val headerRow = sheet.createRow(HEADER_ROW)
        excelHeader.forEachIndexed{index, headerStyle ->
            val headerFont = workbook.createFont()
            val headerCellStyle = workbook.createCellStyle()
            headerCellStyle.setFont(headerFont)
            headerFont.bold=headerStyle.bold
            headerFont.fontHeightInPoints=headerStyle.fontSize
            headerFont.color=headerStyle.fontColor
            headerCellStyle.fillForegroundColor=headerStyle.backColor //배경색
            headerCellStyle.fillPattern= FillPatternType.SOLID_FOREGROUND //배경색채우기방식
            //셀 테두리
            headerCellStyle.borderBottom=BorderStyle.THIN
            headerCellStyle.borderLeft=BorderStyle.THIN
            headerCellStyle.borderRight=BorderStyle.THIN
            headerCellStyle.borderTop=BorderStyle.THIN
            val cell = headerRow.createCell(index)
            cell.setCellValue(headerStyle.data)
            cell.cellStyle = headerCellStyle
        }
    }

    private fun fillData(sheet: SXSSFSheet, rows: List<ExcelRow>, columnSize: Int) {
        sheet.trackAllColumnsForAutoSizing()

        rows.forEachIndexed { index, it ->
            val row = sheet.createRow(index + 1)
            val properties = it.data
            properties.forEachIndexed { propertyIndex, property ->
                row.createCell(propertyIndex).setCellValue(property.toString()) //TODO: 타입 지정 필요할경우 수정
            }
        }

        repeat(columnSize) { col -> sheet.autoSizeColumn(col) }
    }

    /**
     * 각 sheet별 데이터 추출
     */
    private fun getExcelData(sheet: XSSFSheet): ExcelSheet {
        val header = sheet.getRow(HEADER_ROW).map { it.stringCellValue }.toTypedArray()
        val rows: MutableList<ExcelRow> = ArrayList()
        val rowsCount = sheet.physicalNumberOfRows - 1
        for (i: Int in (HEADER_ROW+1)..rowsCount) {
            val row = sheet.getRow(i) //각 행
            rows.add(ExcelRow(row.map { setCell(it) }))
        }
        return ExcelSheet(headers = ExcelHeader(*header), rows = rows, sheetName = sheet.sheetName)

    }

    private fun setCell(cell: Cell): String {
        return when (cell.cellType) {
            CellType.NUMERIC -> cell.numericCellValue.toString()
            CellType.BLANK -> cell.booleanCellValue.toString()
            CellType.ERROR -> cell.errorCellValue.toString()
            else -> cell.stringCellValue
        }
    }

}
