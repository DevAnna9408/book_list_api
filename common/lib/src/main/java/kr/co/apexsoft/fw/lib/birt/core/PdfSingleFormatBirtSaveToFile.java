package kr.co.apexsoft.fw.lib.birt.core;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;

/**
 * Birt view to render PDF-based BIRT reports
 *
 * @author Josh Long
 * @author Jason Weathersby
 */
public class PdfSingleFormatBirtSaveToFile extends AbstractSingleFormatBirtView {

    public PdfSingleFormatBirtSaveToFile() {
        setContentType("application/pdf");
    }

    @Override
    protected RenderOption renderReport(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response,
                                        BirtViewResourcePathCallback resourcePathCallback, Map<String, Object> appContextValuesMap,
                                        String reportName, String format, IRenderOption options) throws Throwable {

        String fileName = (String)map.get("pdfFileName");
        PDFRenderOption pdfOptions = new PDFRenderOption(options);
        pdfOptions.setOutputFormat(IRenderOption.OUTPUT_FORMAT_PDF);
        pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.FIT_TO_PAGE_SIZE);
        pdfOptions.setOption(IPDFRenderOption.PDF_HYPHENATION, true);
        pdfOptions.setOption(IPDFRenderOption.PDF_TEXT_WRAPPING, true);
        pdfOptions.setOutputFileName(map.get("pdfDirectoryFullPath") + "/" + fileName);
        return pdfOptions;
    }
}
