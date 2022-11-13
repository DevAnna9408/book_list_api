package kr.co.book.list.api.service.command.siren

import kr.co.book.list.api.dto.siren.SirenIn
import kr.co.book.list.domain.repository.book.BookRepository
import kr.co.book.list.domain.repository.siren.UserSirenHistoryRepository
import kr.co.book.list.domain.repository.user.UserRepository
import kr.co.book.list.lib.utils.MessageUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SirenCommandService (

    private val userSirenHistoryRepository: UserSirenHistoryRepository,
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository

        ) {
    fun createSiren(sirenIn: SirenIn) {
        val sirenUser = userRepository.getByOid(sirenIn.sirenUserOid)
        val sirenedUser = userRepository.getByOid(sirenIn.sirenedUserOid)
        val sirenedBook = bookRepository.getByOid(sirenIn.sirenedBookOid)

        if (userSirenHistoryRepository.findSirenCountByUserOid(sirenIn.sirenUserOid) >= 10) {
            throw RuntimeException(MessageUtil.getMessage("SIREN_BOOK_COUNT"))
        }

        try {
            userSirenHistoryRepository.save(sirenIn.toEntity(
                sirenUser = sirenUser,
                sirenedUser = sirenedUser,
                sirenedBook = sirenedBook
            ))
            sirenUser.sirenUser()
            sirenedBook.sirenBook()

        } catch (e: RuntimeException) {
            throw RuntimeException(MessageUtil.getMessage("ERROR"))
        }

    }
}
