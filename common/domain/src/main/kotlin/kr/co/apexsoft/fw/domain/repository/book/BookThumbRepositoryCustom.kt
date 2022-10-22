package kr.co.apexsoft.fw.domain.repository.book

interface BookThumbRepositoryCustom {

    fun getByUserOidAndUp(userOid: Long, bookOid: Long): Long
    fun getByUserOidAndDown(userOid: Long, bookOid: Long): Long

}
