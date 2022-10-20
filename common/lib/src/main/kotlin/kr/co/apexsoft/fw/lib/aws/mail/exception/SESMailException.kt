package kr.co.apexsoft.fw.lib.aws.mail.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil

/**
 * SES 메일
 */
class SESMailException (to: String)
    : RuntimeException("[$to ] "+ MessageUtil.getMessage("MAIL_SEND_FAIL"))  {
}
