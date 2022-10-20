package kr.co.apexsoft.fw.lib.csv

data class CsvRow(
    val data: List<String>
) {
    constructor(vararg data: String) : this(data.toList())
}
