package kr.co.apexsoft.fw.lib.aws.mail

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync
import com.amazonaws.services.simpleemail.model.*
import kr.co.apexsoft.fw.lib.aws.mail.exception.SESMailException
import kr.co.apexsoft.fw.lib.aws.mail.exception.SesAsyncHandler
import kr.co.apexsoft.fw.lib.mail.MailDto
import kr.co.apexsoft.fw.lib.mail.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SESMailServiceImpl : MailService {

    @Autowired
    private val sesClient: AmazonSimpleEmailServiceAsync? = null

    @Value("\${aws.ses.sendMail}")
    val sendMail: String? = null

    override fun sendAsync(mailDto: MailDto) {
        sendMail(mailDto, true)
    }

    override fun send(mailDto: MailDto) {
        try {
            sendMail(mailDto, false)
        } catch (e: Exception) {
            throw SESMailException(mailDto.to.toString())
        }
    }

    fun sendMail(mailDto: MailDto, isAsync: Boolean) {
        val emailRequest: SendEmailRequest = toSendRequestDto(mailDto) //ses전송 공통객체 만들기
        val toList: List<String> = mailDto.to
        val listSize = toList.size
        val MAIL_MAX_SIZE = 50 //ses는 한번에 50개씩 메일 전송가능하다
        var firstIndex = 0
        var lastIndex = 0
        var max = if (listSize < MAIL_MAX_SIZE) listSize else MAIL_MAX_SIZE
        while (lastIndex < listSize) {
            lastIndex = firstIndex + max
            val addr: List<String> = ArrayList(toList.subList(firstIndex, lastIndex))
            val destination = Destination()
            destination.setToAddresses(addr) // bcc의 경우 공공기관 메일전송 이슈가 발생
            emailRequest.withDestination(destination)
            if (isAsync) {
                sesClient!!.sendEmailAsync(emailRequest, SesAsyncHandler())
            } else {
                sesClient!!.sendEmail(emailRequest)
            }
            max = if (listSize - lastIndex < MAIL_MAX_SIZE) listSize - lastIndex else MAIL_MAX_SIZE
            firstIndex = lastIndex
        }
    }

    fun toSendRequestDto(mailDto: MailDto): SendEmailRequest {
        return SendEmailRequest()
            .withSource(sendMail)
            .withMessage(newMessage(mailDto))
    }

    fun newMessage(mailDto: MailDto): Message? {
        return Message()
            .withSubject(createContent(mailDto.subject))
            .withBody(
                Body()
                    .withHtml(createContent(mailDto.content))
            ) // content body는 HTML 형식으로 보내기 때문에 withHtml을 사용합니다.
    }

    fun createContent(text: String): Content {
        return Content()
            .withCharset("UTF-8")
            .withData(text)
    }
}
