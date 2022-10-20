package kr.co.apexsoft.fw.lib.birt

import kr.co.apexsoft.fw.lib.birt.core.BirtEngineFactory
import kr.co.apexsoft.fw.lib.birt.core.BirtPdfType
import kr.co.apexsoft.fw.lib.birt.core.CustomAbstractSingleFormatBirtProcessor
import kr.co.apexsoft.fw.lib.birt.core.CustomPdfSingleFormatBirtSaveToFile
import kr.co.apexsoft.fw.lib.birt.exception.BirtGenerateException
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.encryption.AccessPermission
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException
import java.io.InputStream

/**
 * Class Description
 *
 * @author 박지환
 * @since 2022-02-21
 */
@Service
class BirtService(resourceLoader: ResourceLoader) {
    private val birtEngineFactory: BirtEngineFactory = BirtUtil.birtEngineFactory
    private val resourceLoader: ResourceLoader = resourceLoader

    @Value("\${file.baseDir}")
    private val fileBaseDir: String? = null

    /**
     * 테스트용
     */
    fun generateBirtFile() {
        val map: MutableMap<String, Any> = HashMap()
        map["testVal1"] = "sample birt"
        generateBirt(map, "sample", "/output/pdf", "sample.pdf")
    }

    /**
     * Birt 생성 & 파일 로컬 저장
     *
     * @param map
     * @param birtRptFileName
     * @param pdfDirectoryPath
     * @param pdfFileName
     */
    fun generateBirt(
        map: MutableMap<String, Any>, birtRptFileName: String,
        pdfDirectoryPath: String, pdfFileName: String
    ): Map<String, Any> {
        map["reportFormat"] = "PDF"
        map["reportName"] = birtRptFileName
        map["pdfType"] = BirtPdfType.MULTIPLE.codeVal()
        map["pdfDirectoryFullPath"] = fileBaseDir + pdfDirectoryPath
        map["pdfFileName"] = pdfFileName
        val reportEngine = birtEngineFactory.getObject()
        val birtProcessor: CustomAbstractSingleFormatBirtProcessor =
            CustomPdfSingleFormatBirtSaveToFile()
        birtProcessor.setBirtEngine(reportEngine)
        val pathToRptdesignFile = "classpath:reports/$birtRptFileName.rptdesign"
        val designFile = resourceLoader.getResource(pathToRptdesignFile)
        var designFileInputStream: InputStream? = null
        try {
            designFileInputStream = designFile.inputStream
            birtProcessor.setDesignFileInputStream(designFileInputStream)
            try {
                birtProcessor.createReport(map)
            } catch (e: Exception) {
                throw BirtGenerateException(e.message, "파일 생성에 실패했습니다")
            }
//            setPasswordPdf(map) // 편집시 비밀번호 설정
        } catch (e: Exception) {
            throw BirtGenerateException(e.message, "파일 생성에 실패했습니다")
        } finally {
            if (designFileInputStream != null) {
                try {
                    designFileInputStream.close()
                } catch (e: IOException) {
                    throw BirtGenerateException("파일 생성에 실패했습니다")
                }
            }
        }
        return map
    }

    /**
     * 편집시 비밀번호 설정
     *
     * @param map
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun setPasswordPdf(map: Map<String, Any>) {
        val fileDir = map["pdfDirectoryFullPath"] as String?
        val fileName = map["pdfFileName"] as String?
        val file = File(fileDir, fileName)
        val originPdf = PDDocument.load(file)
        val ap = AccessPermission()
        ap.setCanModify(false)
        ap.setReadOnly()
        val spp = StandardProtectionPolicy("apexSecret", "", ap)
        originPdf.protect(spp)
        originPdf.save(file.absoluteFile)
        originPdf.close()
    }



}
