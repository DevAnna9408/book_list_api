package kr.co.apexsoft.fw.lib.birt


import kr.co.apexsoft.fw.lib.birt.core.BirtEngineFactory
import kr.co.apexsoft.fw.lib.birt.core.PdfSingleFormatBirtSaveToFile
import kr.co.apexsoft.fw.lib.birt.core.PdfSingleFormatBirtView
import java.util.logging.Level


object BirtUtil {

    val birtEngineFactory by lazy {
        val factory = BirtEngineFactory()
        factory.setLogLevel(Level.SEVERE)
        factory
    }

    val pdfSingleFormatBirtView by lazy {
        val pdfSingleFormatBirtView =
            PdfSingleFormatBirtView()
        pdfSingleFormatBirtView.setBirtEngine(birtEngineFactory.getObject())
        pdfSingleFormatBirtView.setReportFormatRequestParameter("reportFormat")
        pdfSingleFormatBirtView.setReportNameRequestParameter("reportName")
        pdfSingleFormatBirtView.setDocumentNameRequestParameter("documentName")
        pdfSingleFormatBirtView.setReportsDirectory("reports")
        pdfSingleFormatBirtView
    }

    val pdfSingleFormatBirtSaveToFile by lazy {
        val pdfSingleFormatBirtSaveToFile =
            PdfSingleFormatBirtSaveToFile()
        pdfSingleFormatBirtSaveToFile.setBirtEngine(birtEngineFactory.getObject())
        pdfSingleFormatBirtSaveToFile.setReportFormatRequestParameter("reportFormat")
        pdfSingleFormatBirtSaveToFile.setReportNameRequestParameter("reportName")
        pdfSingleFormatBirtSaveToFile.setDocumentNameRequestParameter("documentName")
        pdfSingleFormatBirtSaveToFile.setReportsDirectory("reports")
        pdfSingleFormatBirtSaveToFile
    }
}

