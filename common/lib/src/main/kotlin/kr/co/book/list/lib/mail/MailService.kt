package kr.co.book.list.lib.mail

interface MailService {
    fun sendAsync(mailDto: MailDto)

    fun send(mailDto: MailDto)

}
