package kr.co.apexsoft.fw.lib.mail

import kr.co.apexsoft.fw.lib.mail.exception.MailTemplateException
import org.apache.http.util.TextUtils
import org.springframework.core.io.ResourceLoader
import org.springframework.util.StringUtils
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object MailTemplateUtil {

    fun getMailContent(
        templateId: String,
        replacementParams: Map<String, String>,
        resourceLoader: ResourceLoader
    ): String {
        val template = loadTemplate(templateId, resourceLoader)
        return createHtmlMailContent(template, replacementParams)
    }


    /**
     * templates/templates.email-templates/ 폴더 의 html 템플릿 파일 데이터 추출
     *
     * @param templateId
     * @param resourceLoader
     * @return
     * @throws Exception
     */
    private fun loadTemplate(templateId: String, resourceLoader: ResourceLoader): String {
        var content: String
        val bufferedReader: BufferedReader
        val buffer = StringBuffer()
        var inputStream: InputStream? = null
        val designFile =
            resourceLoader.getResource("classpath:/templates/email-templates/$templateId")
        try {
            inputStream = designFile.inputStream
            bufferedReader = BufferedReader(InputStreamReader(inputStream, "utf-8"))
            var line = bufferedReader.readLine()
            while (line != null) {
                line = line.trim()
                if (!TextUtils.isEmpty(line)) {
                    buffer.append(line).append('\n')
                }
                line = bufferedReader.readLine()
            }
        } catch (e: Exception) {
            println(e.toString())
            throw MailTemplateException()
        }
        content = buffer.toString()
        return content
    }

    /**
     * html 템플릿의 {{key}} 해당 value넣기
     *
     * @param replacements
     * @return
     */
    private fun createHtmlMailContent(
        template: String,
        replacements: Map<String, String>
    ): String {
        var cTemplate = template
        if (StringUtils.hasText(cTemplate)) {
            for ((key, value) in replacements) {
                cTemplate = cTemplate.replace("{{$key}}", value)
            }
        }
        return cTemplate
    }
}
