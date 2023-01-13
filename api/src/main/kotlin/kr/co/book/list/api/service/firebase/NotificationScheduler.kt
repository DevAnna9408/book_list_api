package kr.co.book.list.api.service.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import kr.co.book.list.api.dto.firebase.NotificationMessage
import kr.co.book.list.api.service.command.book.BookCommandService
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.IOException
import javax.annotation.PostConstruct

@Service
class NotificationScheduler (

    private val bookCommandService: BookCommandService

) {

    private var instance: FirebaseMessaging? = null

    @PostConstruct
    @Throws(IOException::class)
    fun firebaseSetting() {
        val googleCredentials =
            GoogleCredentials.fromStream(ClassPathResource("firebase/booklet-4f7dc-firebase-adminsdk-ahir3-570a89c9d8.json").inputStream)
                .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging"))
        val secondaryAppConfig = FirebaseOptions.builder()
            .setCredentials(googleCredentials)
            .build()
        val app = FirebaseApp.initializeApp(secondaryAppConfig)
        instance = FirebaseMessaging.getInstance(app)
    }

    fun getRandomMessage(): NotificationMessage {
        val title = "${bookCommandService.getRandomBookNoAuth().title}_${bookCommandService.getRandomBookNoAuth().author}"
        val message = bookCommandService.getRandomBookNoAuth().content
        return NotificationMessage(title, message)
    }

    @Scheduled(cron = "0 0 08 * * ?")
    @Throws(FirebaseMessagingException::class)
    fun pushMorningAlarm() {
        pushAlarm(getRandomMessage())
    }

    @Scheduled(cron = "0 0 13 * * ?")
    @Throws(FirebaseMessagingException::class)
    fun pushLunchAlarm() {
        pushAlarm(getRandomMessage())
    }

    @Scheduled(cron = "0 00 19 * * ?")
    @Throws(FirebaseMessagingException::class)
    fun pushDinnerAlarm() {
        pushAlarm(getRandomMessage())
    }

    @Throws(FirebaseMessagingException::class)
    fun pushAlarm(data: NotificationMessage) {
        val message = getMessage(data)
        sendMessage(message)
    }

    fun getMessage(data: NotificationMessage): Message {
        val notification = Notification.builder().setTitle(data.title).setBody(data.message).build()
        val builder = Message.builder()
        val topic = "book_let_topic"
        return builder.setTopic(topic).setNotification(notification).build()
    }

    @Throws(FirebaseMessagingException::class)
    fun sendMessage(message: Message?): String? {
        return instance!!.send(message)
    }

}
