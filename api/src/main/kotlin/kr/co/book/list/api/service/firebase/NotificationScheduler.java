package kr.co.book.list.api.service.firebase;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import kr.co.book.list.api.service.command.book.BookCommandService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

public class NotificationScheduler {

    private FirebaseMessaging instance;
    private BookCommandService bookCommandService;

    @PostConstruct
    public void firebaseSetting() throws IOException {
        String fireBaseCreateScoped = "https://www.googleapis.com/auth/firebase.messaging";
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("firebase/booklet-4f7dc-firebase-adminsdk-ahir3-2fca9b80b1.json").getInputStream())
                .createScoped((List.of(fireBaseCreateScoped)));
        FirebaseOptions secondaryAppConfig = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(secondaryAppConfig);
        this.instance = FirebaseMessaging.getInstance(app);
    }

    public NotificationMessage getRandomMessage() {

        String title = "따북!";
        String message = bookCommandService.getRandomBookNoAuth().getContent();
        return new NotificationMessage(title, message);
    }

    @Scheduled(cron = "0 0 08 * * ?")
    public void pushMorningDietAlarm() throws FirebaseMessagingException {
        NotificationMessage notificationMessage = new NotificationMessage(
                getRandomMessage().getTitle(),
                getRandomMessage().getMessage()
        );
        pushAlarm(notificationMessage);
    }

    @Scheduled(cron = "0 0 14 * * ?")
    public void pushLunchDietAlarm() throws FirebaseMessagingException {
        NotificationMessage notificationMessage = new NotificationMessage(
                getRandomMessage().getTitle(),
                getRandomMessage().getMessage()
        );
        pushAlarm(notificationMessage);
    }

    @Scheduled(cron = "0 0 19 * * ?")
    public void pushDinnerDietAlarm() throws FirebaseMessagingException {
        NotificationMessage notificationMessage = new NotificationMessage(
                getRandomMessage().getTitle(),
                getRandomMessage().getMessage()
        );
        pushAlarm(notificationMessage);
    }

    private void pushAlarm(NotificationMessage data) throws FirebaseMessagingException {
        Message message = getMessage(data);
        sendMessage(message);
    }

    private Message getMessage(NotificationMessage data) {
        Notification notification = Notification.builder().setTitle(data.getTitle()).setBody(data.getMessage()).build();
        Message.Builder builder = Message.builder();
        String topic = "diet_record_notification";
        return builder.setTopic(topic).setNotification(notification).build();
    }

    public void sendMessage(Message message) throws FirebaseMessagingException {
        this.instance.send(message);
    }

}
