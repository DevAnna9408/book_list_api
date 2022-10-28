package kr.co.book.list.lib.pdf

import kr.co.book.list.lib.pdf.exception.PDFEncryptedException
import kr.co.book.list.lib.pdf.exception.PDFPageCountException
import kr.co.book.list.lib.pdf.exception.PDFPageException
import kr.co.book.list.lib.pdf.exception.PDFPageInsertException
import kr.co.book.list.lib.utils.MessageUtil
import lombok.RequiredArgsConstructor
import org.apache.commons.lang3.StringUtils
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.encryption.AccessPermission
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.util.Matrix
import org.slf4j.LoggerFactory
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException

@Service
@RequiredArgsConstructor
class PDFPageService (resourceLoader: ResourceLoader){

    private val resourceLoader: ResourceLoader = resourceLoader

    /**
     * 직접 업로드 파일
     * 암호화 여부 체크
     * @param pdfFile
     * @param applNo
     */
    fun isEncrypted(pdfFile: File, applNo: Long?) {
        var pdDocument: PDDocument? = null
        try {
            pdDocument = PDDocument.load(pdfFile)
            if (pdDocument.isEncrypted) throw PDFEncryptedException(pdfFile.name)
        } catch (e: IOException) {
            throw PDFPageException(MessageUtil.getMessage("PDF_FILE_FAIL"), pdfFile.name)
        } finally {
            if (pdDocument != null) {
                try {
                    pdDocument.close()
                } catch (e: IOException) {
                    throw PDFPageException(pdfFile.name,
                        MessageUtil.getMessage("PDF_FILE_FAIL")
                    )
                }
            }
        }
    }

    /**
     * pdf 파일의 페이지수 계산
     *
     * @param pdfFile
     * @return
     */
    fun getPdfPageCount(pdfFile: File): Int {
        var pageCounts = -1
        var pdDocument: PDDocument? = null
        try {
            pdDocument = PDDocument.load(pdfFile)
            pageCounts = pdDocument.numberOfPages
        } catch (e: IOException) {
            throw PDFPageCountException(pdfFile.name)
        } finally {
            if (pdDocument != null) {
                try {
                    pdDocument.close()
                } catch (e: IOException) {
                    throw PDFPageCountException(pdfFile.name)
                }
            }
        }
        return pageCounts
    }

    /**
     * PDF 파일에 텍스트를 추가한다
     * 우상단  ' 현재 페이지 / 전체 페이지 '
     * 좌상단 'PDFPageInfo'
     *
     * @param file        쪽수를 매길 파일
     * @param startPage   쪽수를 매기기 시작할 페이지(0이 아니라 1부터 시작하는 숫자)
     * @param startNo     시작 쪽수 번호
     * @param endNo       전체 쪽수 번호, 0이나 음수를 입력하면 해당 파일의 페이지수를 기준으로 startPage를 고려해서 자동 계산
     * @param pdfPageInfo pdf 추가 입력 정보
     * @return
     */
    fun insertPDFPageInfo(
        file: File,
        startPage: Int,
        startNo: Int,
        endNo: Int,
        pdfPageInfo: PDFPageInfo
    ): File {
        val targetFilePath = getTargetFilePath(file)
        try {
            val pdDocument = PDDocument.load(file)

            //pdf 파일 읽기전용 세팅
            val ap = AccessPermission()
            ap.setCanModify(false)
            ap.setReadOnly()
            val spp = StandardProtectionPolicy("apexSecret", "", ap)

            //TODO : NanumGothicCoding: 영문.숫자.한글 지원 폰트로 다른언어지원시 변경 필요
            val fontstream =
                resourceLoader.getResource("classpath:/pdf_font/NanumGothicCoding-Bold.ttf").inputStream
            val fontNanum = PDType0Font.load(pdDocument, fontstream)
            val fontSize = 13.0f
            val pageTree = pdDocument.pages
            val length = if (endNo > 0) endNo else pdDocument.numberOfPages - startPage + 1
            val it: Iterator<PDPage> = pageTree.iterator()
            var i = 1
            while (i < startPage && it.hasNext()) {
                it.next()
                i++
            }
            while (it.hasNext()) {
                val page = it.next()
                val pageSize = page.mediaBox
                val strPage =
                    StringBuilder().append(i++ + startNo - startPage).append('/').append(length)
                        .toString()
                val stringWidth = fontNanum.getStringWidth(strPage) * fontSize / 1000f
                val pageWidth = pageSize.width
                val pageHeight = pageSize.height
                val m4Position = Matrix()
                m4Position.translate(pageWidth - stringWidth - 15, pageHeight - 20)
                val contentStream = PDPageContentStream(pdDocument, page, true, true, true)
                contentStream.beginText() //글쓰기 시작
                contentStream.setFont(fontNanum, fontSize) //폰트 설정

                //글쓰기
                contentStream.setTextMatrix(m4Position)
                //우측 상단 페이지
                contentStream.showText(strPage)
                if (StringUtils.isNotBlank(pdfPageInfo.getLeftTop())) {
                    contentStream.setFont(fontNanum, 12f)
                    contentStream.newLineAtOffset(-pageWidth + stringWidth + 30, 0f)
                    contentStream.showText(java.lang.String.valueOf(pdfPageInfo.getLeftTop()))
                }
                contentStream.endText() // 글쓰기 종료
                contentStream.close()
            }
            //암호화 여부 체크
            if (pdDocument.isEncrypted) throw PDFEncryptedException(file.name)
//            pdDocument.protect(spp) // 읽기 전용
            pdDocument.save(targetFilePath)
        } catch (e: Exception) {
            //암호화 pdf . 한글.영문.숫자 외
            throw PDFPageInsertException(targetFilePath)
        }
        return File(targetFilePath) // S3업로드 후 로컬 파일삭제 필수
    }

    fun insertPDFPageInfo(file: File, startNo: Int, endNo: Int, pdfPageInfo: PDFPageInfo): File {
        return insertPDFPageInfo(file, 1, startNo, endNo, pdfPageInfo)
    }

    // 생성할 파일 전체 경로 반환
    private fun getTargetFilePath(file: File): String {
        return file.absolutePath
    }

    fun deletePageNumberedPDF(file: File) {
        val targetFilePath = getTargetFilePath(file)
        val numberedFile = File(targetFilePath)
        if (numberedFile.exists()) numberedFile.delete()
    }

    /**
     * pdf파일들에 정보삽입
     * 전체 페이징 + 추가 정보
     * @param uploaded
     * @param pageInfo
     * @return
     */
    fun getPageNumberedPDFs(uploaded: List<File>, pageInfo: PDFPageInfo): List<File> {
        var totalpageCount = 0
        var accumulatedPages = 1
        val fileList: MutableList<File> = ArrayList()
        for (item in uploaded) {
            val pageCounts = getPdfPageCount(item)
            totalpageCount += pageCounts
        }
        for (item in uploaded) {
            fileList.add(insertPDFPageInfo(item, 1, accumulatedPages, totalpageCount, pageInfo))
            accumulatedPages += getPdfPageCount(item)
        }
        return fileList
    }

    companion object {
        private val logger = LoggerFactory.getLogger(PDFPageService::class.java)
    }
}
