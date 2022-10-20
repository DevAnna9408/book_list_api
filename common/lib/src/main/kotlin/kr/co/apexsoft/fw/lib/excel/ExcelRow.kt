package kr.co.apexsoft.fw.lib.excel

data class ExcelRow(
    val data: List<Any>
) {
    constructor(vararg data: Any) : this(data.toList())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExcelRow
        if(data.containsAll(other.data))return true

        return false
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}
