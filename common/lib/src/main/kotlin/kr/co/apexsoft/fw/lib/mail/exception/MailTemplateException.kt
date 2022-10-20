package kr.co.apexsoft.fw.lib.mail.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil

class MailTemplateException
    : RuntimeException(MessageUtil.getMessage("MAIL_SEND_FAIL"))  {
}

