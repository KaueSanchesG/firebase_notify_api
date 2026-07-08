package br.com.kasag.oraklast.service;

import br.com.kasag.oraklast.dto.NotificationMessageDTO;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FCMService {

    public void sendNotification(NotificationMessageDTO notificationDTO) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(notificationDTO.title())
                .setBody(notificationDTO.message())
                .setImage("./assets/logo.png")
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setTopic(notificationDTO.topic())
                .build();

        FirebaseMessaging.getInstance().send(message);
    }


}
