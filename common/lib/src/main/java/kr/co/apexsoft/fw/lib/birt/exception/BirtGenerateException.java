package kr.co.apexsoft.fw.lib.birt.exception;


/**
 * Birt 생성
 */
public class BirtGenerateException extends RuntimeException {

    private String message;

    public BirtGenerateException() {
        super();
    }

    public BirtGenerateException(String warningMessage) {
        super(warningMessage);
    }

    public BirtGenerateException(String message, String warningMessage) {
        super(warningMessage);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
