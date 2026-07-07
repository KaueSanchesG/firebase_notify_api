package br.com.kasag.oraklast.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FCMService {
    public static void sendNotification(Map<String, Object> payload) {


//        try {
//            Notification notification = Notification.builder()
//                    .setTitle("warning")
//                    .setBody()
//                    .build();
//
//            Message message = Message.builder()
//                    .setNotification(notification)
//                    .setTopic()
//                    .build();
//
//            String response = FirebaseMessaging.getInstance().send(message);
//
//            System.out.println("Sucesso, id da msg: " + response);
//        } catch (FirebaseMessagingException e) {
//            e.printStackTrace();
//            System.out.println("Erro ao enviar notificação: " + e.getMessage());
//        }
    }
}
