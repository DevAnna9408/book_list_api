package kr.co.book.list.lib.mail.exception

import kr.co.book.list.lib.utils.MessageUtil

class MailTemplateException
    : RuntimeException(MessageUtil.getMessage("MAIL_SEND_FAIL"))  {
}

