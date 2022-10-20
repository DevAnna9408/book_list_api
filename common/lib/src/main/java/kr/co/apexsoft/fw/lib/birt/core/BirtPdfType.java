package kr.co.apexsoft.fw.lib.birt.core;

/**
 * Class Description
 *
 * @author 김혜연
 * @since 2019-12-09
 */
public enum BirtPdfType {
    MULTIPLE("OUTPUT_TO_MULTIPLE_PAGES"), FIT ("FIT_TO_PAGE_SIZE");

    private String codeVal;

    private BirtPdfType() {

    }

    private BirtPdfType(String codeVal) {
        this.codeVal = codeVal;
    }

    public String codeVal() {
        return this.codeVal;
    }
}
