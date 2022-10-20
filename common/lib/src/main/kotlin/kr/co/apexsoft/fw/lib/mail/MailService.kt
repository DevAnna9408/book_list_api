package kr.co.apexsoft.fw.lib.mail

interface MailService {
    fun sendAsync(mailDto: MailDto)

    fun send(mailDto: MailDto)

}
