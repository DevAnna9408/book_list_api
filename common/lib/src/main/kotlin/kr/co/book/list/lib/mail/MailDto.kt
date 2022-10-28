package kr.co.book.list.lib.mail

import kr.co.book.list.lib.utils.ValidUtil
import org.springframework.core.io.ResourceLoader
import java.util.stream.Collectors
import kr.co.book.list.lib.mail.MailTemplateUtil

class MailDto {
    lateinit var from: String
    lateinit var to: List<String>
    lateinit var subject : String // 제목
    lateinit var templateId : String //classpath:/templates/email-templates/메일 템플릿 파일명
    lateinit var replacements: Map<String, String>
    lateinit var content: String

    constructor()

    constructor(
        to: List<String>,
        subject: String,
        templateId: String?,
        replacements: Map<String, String>,
        resourceLoader: ResourceLoader
    ) {
        this.to = checkMailAddr(to)
        this.subject = "[Apexsoft] $subject"
        this.templateId = templateId!!
        this.replacements = replacements
        content = MailTemplateUtil.getMailContent(templateId, replacements, resourceLoader)
    }

    private fun checkMailAddr(toMailAddr: List<String>): List<String> {
        return toMailAddr.stream()
            .filter(ValidUtil::isMail)
            .collect(Collectors.toList())
    }
}
