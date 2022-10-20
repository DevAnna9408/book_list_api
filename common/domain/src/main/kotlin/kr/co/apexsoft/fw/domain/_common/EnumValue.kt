package kr.co.apexsoft.fw.domain._common


data class EnumValue(val code: String, val korName: String, val engName: String) {
    constructor(enumModel: EnumModel) : this(
        enumModel.getCode(), enumModel.getKorName(), enumModel.getEngName()
    )

}
