package br.com.kasag.oraklast.service;

import br.com.kasag.oraklast.dto.ForecastUnitedDataDTO;
import br.com.kasag.oraklast.dto.NotificationDateDTO;
import br.com.kasag.oraklast.dto.NotificationPayloadDTO;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {
    public FirestoreService(Firestore db) {
        this.db = db;
    }

    private final Firestore db;

    public void updatePoints(List<ForecastUnitedDataDTO> payload) throws ExecutionException, InterruptedException {
        for (ForecastUnitedDataDTO pointData : payload) {
            db.collection("points")
                    .document(pointData.pointId())
                    .set(pointData)
                    .get();
        }
    }

    public void updateNotifications(List<NotificationPayloadDTO> payload) throws ExecutionException, InterruptedException {
        for (NotificationPayloadDTO pointNotification : payload) {
            db.collection("notifications")
                    .document(pointNotification.pointId())
                    .set(pointNotification)
                    .get();
        }
    }

    public List<Map<String, Object>> getRawNotifications() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> promise = db.collection("notifications").get();
        List<QueryDocumentSnapshot> documents = promise.get().getDocuments();

        List<Map<String, Object>> notificationsMap = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documents) {
            Map<String, Object> pointNotificationMap = new HashMap<>();

            pointNotificationMap.put("pointId", doc.getId());

            List<Map<String, Object>> rawInfoList = (List<Map<String, Object>>) doc.get("notificationInfo");

            pointNotificationMap.put("notificationInfo", rawInfoList != null? rawInfoList : new ArrayList<>());

            notificationsMap.add(pointNotificationMap);
        }
        return notificationsMap;
    }
}
