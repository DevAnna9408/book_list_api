package kr.co.book.list.api.service.firebase;

public class NotificationMessage {

    private String title;
    private String message;

    public NotificationMessage() {

    }

    public NotificationMessage(String title, String message) {
        super();
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
