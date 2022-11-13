package kr.co.book.list.domain.repository.siren

interface UserSirenHistoryRepositoryCustom {

    fun findSirenCountByUserOid(userOid: Long): Long

}
