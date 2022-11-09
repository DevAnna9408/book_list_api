package kr.co.book.list.domain.model

import kr.co.book.list.domain._common.EnumModel



enum class Role(private val korName: String, private val engName: String) : EnumModel {

    ROLE_USER("사용자", "user"),
    ROLE_MANAGER("매니저", "MANAGER"),
    ROLE_SYS_ADMIN("시스템관리자", "systemAdmin");

    override fun getKorName(): String {
        return korName
    }

    override fun getEngName(): String {
        return engName
    }

    override fun getCode(): String {
        return name
    }
}

enum class LockReason(private val korName: String, private val engName: String) : EnumModel {

    ACCUMULATED_REPORTS("신고누적", "ACCUMULATED REPORTS");

    override fun getKorName(): String {
        return korName
    }

    override fun getEngName(): String {
        return engName
    }

    override fun getCode(): String {
        return name
    }
}

