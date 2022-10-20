package kr.co.apexsoft.fw.lib.aws.mail.exception

import com.amazonaws.handlers.AsyncHandler
import com.amazonaws.services.simpleemail.model.SendEmailRequest
import com.amazonaws.services.simpleemail.model.SendEmailResult
import org.slf4j.LoggerFactory

class SesAsyncHandler : AsyncHandler<SendEmailRequest, SendEmailResult> {
    private val logger = LoggerFactory.getLogger(SesAsyncHandler::class.java)
    override fun onError(exception: Exception) {
        logger.error("SES send Error:" + exception.message)
    } //TODO : 에러 발생시 알림 OR DB 처리 논의 필요

    override fun onSuccess(request: SendEmailRequest, result: SendEmailResult) {
        if (logger.isDebugEnabled) {
            logger.debug("Email sent to: {}", request.destination.toAddresses)
            logger.debug("SES SendEmailResult messageId: [{}]", result.messageId)
        }
    }
}
