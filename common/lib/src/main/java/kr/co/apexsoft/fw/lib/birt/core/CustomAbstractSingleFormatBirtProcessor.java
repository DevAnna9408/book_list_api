package kr.co.apexsoft.fw.lib.birt.core;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IHTMLActionHandler;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.birt.report.model.api.IModuleOption;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Base class for BIRT-based
 */
abstract public class CustomAbstractSingleFormatBirtProcessor implements InitializingBean {

    public static interface BirtViewResourcePathCallback {

        String baseImageUrl(ServletContext sc, HttpServletRequest r, String reportName) throws Throwable;

        String baseUrl(ServletContext sc, HttpServletRequest r, String reportName) throws Throwable;

        String pathForReport(ServletContext servletContext, HttpServletRequest r, String reportName) throws Throwable;

        String imageDirectory(ServletContext sc, HttpServletRequest request, String reportName);

        String resourceDirectory(ServletContext sc, HttpServletRequest request, String reportName);
    }

    public static final int RUNRENDERTASK = 0;

    private static final String DEFAULT_REPORT_EXT = ".rptdesign";

    private String reportName;
    private IReportEngine birtEngine;

    private int taskType = this.RUNRENDERTASK;

    private String reportNameRequestParameter = "reportName";

    private String imagesDirectory = "images";

    private String reportsDirectory = "";

    private String resourceDirectory = "resources";

    private IRenderOption renderOption;

    private String reportFormatRequestParameter = "reportFormat";

    protected BirtViewResourcePathCallback birtViewResourcePathCallback;

    protected IHTMLActionHandler actionHandler;

    private String requestEncoding = "UTF-8";

    private String renderRange = null;

    private String reportOutputFormat;

    private InputStream designFileInputStream;

    private Map<String, Object> reportParameters;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setReportOutputFormat(String format){
        this.reportOutputFormat = format;
    }

    public void setDesignFileInputStream(InputStream designFileInputStream) {
        this.designFileInputStream = designFileInputStream;
    }

    /**
     * This method allows you to set the implementation of the Resource callback class for implementing
     * location logic for resource folder, image folder, reports folder, baseURL and baseImageURL
     */
    public void setBirtViewResourcePathCallback(BirtViewResourcePathCallback birtViewResourcePathCallback) {
        this.birtViewResourcePathCallback = birtViewResourcePathCallback;
    }

    /**
     * Set the instance of the BIRT Engine
     */
    public void setBirtEngine(IReportEngine birtEngine) {
        this.birtEngine = birtEngine;
    }

    /**
     * Set the resource directory that contains birt libraries, images, style sheets for the reports
     * by default the Resources folder will be checked
     */
    public void setResourceDirectory(String resourceDirectory) {
        this.resourceDirectory = resourceDirectory;
    }

    /**
     * Set the folder within the web app that will contain reports
     */
    public void setReportsDirectory(String reportsDirectory) {
        this.reportsDirectory = reportsDirectory;
    }

    /**
     * Set the images directory that engine will use to generate temporary images for the reports
     * by default the images directory will be used
     */
    public void setImagesDirectory(String imagesDirectory) {
        this.imagesDirectory = imagesDirectory;
    }

    /**
     * Sets the engine to either runandrender a report using one task or
     * to generate a rptdocument first with a runtask and then followed by a render task
     * default operation is to use a runandrender task
     */
    public void setTaskType(int taskType) {
        this.taskType = taskType;

    }

    /**
     * Set the page range string for reports that use a run then render task.
     * eg 1-3, 1,3,4
     */
    public void setRenderRange(String renderRange) {
        this.renderRange = renderRange;

    }

    /**
     * This method allows setting the render options for rendering reports
     *
     * @param renderOption
     */
    public void setRenderOption(IRenderOption renderOption) {
        this.renderOption = renderOption;
    }

    /**
     * Perform _common validation on the state of this object
     *
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(this.requestEncoding, "the 'requestEncoding' must be set");
        Assert.hasText(this.reportFormatRequestParameter, "the 'reportFormatRequestParameter' must not be null");
        Assert.isTrue(StringUtils.hasText(this.reportName) || StringUtils.hasText(this.reportNameRequestParameter), "the 'reportName' or the 'reportNameRequestParameter' must not be null");

        if (null == this.renderOption)
            this.renderOption = new RenderOption();
    }

    /**
     * 자식 클래스에서 미리 보기 또는 파일 저장 등 필요에 따라 구현
     *
     * @param map
     * @param resourcePathCallback
     * @param reportName
     * @param format
     * @param options
     * @return
     * @throws Throwable
     */
    abstract protected RenderOption renderReport( Map<String, Object> map,
                                                  BirtViewResourcePathCallback resourcePathCallback,
                                                  String reportName, String format, IRenderOption options ) throws Throwable;

    private String canonicalizeName(String reportName) {
        if (!StringUtils.hasText(reportName))
            return null;

        return !reportName.toLowerCase().endsWith( DEFAULT_REPORT_EXT ) ? reportName + DEFAULT_REPORT_EXT : reportName;
    }

    /**
     * 외부에서 BirtService를 직접 호출하고,
     * BirtService에서 본 메서드를 호출해서 Birt 파일을 미리보기 또는 생성한다.
     *
     * @param modelData Birt 보고서에 표시될 데이타
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void createReport(Map<String, Object> modelData) throws Exception {
        IReportRunnable runnable = null;
        try {

            if (this.reportParameters == null) {
                this.reportParameters = new HashMap<String, Object>();
            }

            for (String k : modelData.keySet()) {
                this.reportParameters.put(k, modelData.get(k));
            }

            String reportName = (String)modelData.get("reportName");
            String fullReportName = canonicalizeName(reportName);

            String format = (String)modelData.get("reportFormat");

            Map<String, Object> mapOfOptions = new HashMap<String, Object>();
            mapOfOptions.put(IModuleOption.RESOURCE_FOLDER_KEY, "get Resource Directory");
            mapOfOptions.put(IModuleOption.PARSER_SEMANTIC_CHECK_KEY, Boolean.FALSE);

            IEngineTask task = null;
            runnable = birtEngine.openReportDesign(fullReportName, designFileInputStream, mapOfOptions);

            if (runnable != null && this.taskType == CustomAbstractSingleFormatBirtProcessor.RUNRENDERTASK) {
                task = birtEngine.createRunAndRenderTask(runnable);
                task.setParameterValues(discoverAndSetParameters(runnable));
                IRunAndRenderTask runAndRenderTask = (IRunAndRenderTask) task;
                IRenderOption options = null == this.renderOption ? new RenderOption() : this.renderOption;
                IRenderOption returnedRenderOptions = renderReport(modelData, this.birtViewResourcePathCallback,
                        reportName, format, options);
                for (String k : modelData.keySet())
                    runAndRenderTask.getAppContext().put(k, modelData.get(k));
                runAndRenderTask.setRenderOption(returnedRenderOptions);
                runAndRenderTask.run();
                runAndRenderTask.close();
            }
        } catch (Throwable th) {
            throw new RuntimeException(th); // nothing useful to do here
        }
    }

    private Map<String, Object> discoverAndSetParameters(IReportRunnable report) throws Throwable {

        Map<String, Object> parms = new HashMap<String, Object>();
        IGetParameterDefinitionTask task = birtEngine.createGetParameterDefinitionTask(report);
        @SuppressWarnings("unchecked")
        Collection<IParameterDefnBase> params = task.getParameterDefns(true);
        for (IParameterDefnBase param : params) {
            Assert.isInstanceOf(IScalarParameterDefn.class, param, "the parameter must be assignable to " + IScalarParameterDefn.class.getName());
            IScalarParameterDefn scalar = (IScalarParameterDefn) param;
            if (this.reportParameters != null && this.reportParameters.get(param.getName()) != null) {
                Object value = this.reportParameters.get(param.getName());
                parms.put(param.getName(), value);
            }
        }
        task.close();
        return parms;
    }
}

