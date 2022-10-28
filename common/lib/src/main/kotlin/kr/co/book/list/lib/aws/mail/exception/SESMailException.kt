package kr.co.book.list.lib.aws.mail.exception

import kr.co.book.list.lib.utils.MessageUtil

/**
 * SES 메일
 */
class SESMailException (to: String)
    : RuntimeException("[$to ] "+ MessageUtil.getMessage("MAIL_SEND_FAIL"))  {
}
