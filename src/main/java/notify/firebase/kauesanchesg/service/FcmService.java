package notify.firebase.kauesanchesg.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import notify.firebase.kauesanchesg.domain.entity.NeighborhoodEntity;
import notify.firebase.kauesanchesg.dto.response.WarningResponse;
import org.springframework.stereotype.Service;

@Service
public class FcmService {

    public void sendNotification(WarningResponse warning, NeighborhoodEntity entity) {
        try {
            Notification notification = Notification.builder()
                    .setTitle("warning")
                    .setBody(warning.message())
                    .build();

            Message message = Message.builder()
                    .setNotification(notification)
                    .setTopic(entity.getName())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);

            System.out.println("Sucesso, id da msg: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}
