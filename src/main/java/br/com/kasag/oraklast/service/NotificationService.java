package br.com.kasag.oraklast.service;

import br.com.kasag.oraklast.dto.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {

    @Autowired
    private FCMService fcmService;

    @Autowired
    private FirestoreService firestoreService;

    /**
     * Method to validate how far for the point's max history value is the forecasted discharge
     */
    public void doNotificationsRoutine(List<ForecastUnitedDataDTO> payload) {
        List<NotificationPayloadDTO> parsedNotificationList = parseForecastsToNotification(payload);
        List<Map<String, Object>> syncedNotificationMap = new ArrayList<>();

        List<NotificationPayloadDTO> updatedNotificationList = new ArrayList<>();

        try {
            syncedNotificationMap = firestoreService.getRawNotifications();
        }catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (NotificationPayloadDTO parsed : parsedNotificationList){
            String pointId = parsed.pointId();

            List<NotificationDateDTO> notificationDatesToSend = new ArrayList<>();

            for (NotificationDateDTO notificationDate : parsed.notificationInfo()){
                String date = notificationDate.date();
                int newLvl = notificationDate.lvl();

                int higherLvlIndex = -1;

                for (Map<String, Object> cloudPointNotifications : syncedNotificationMap){
                    if (pointId.equals(cloudPointNotifications.get("pointId"))){
                        List<Map<String, Object>> infoList = (List<Map<String, Object>>) cloudPointNotifications.get("notificationInfo");

                        if (infoList != null){
                            for (Map<String, Object> cloudPointNotification : infoList){
                                if (date.equals(cloudPointNotification.get("date"))){
                                    Long unparsedLvl = (Long) cloudPointNotification.get("notificationLvl");
                                    int lvl = (unparsedLvl != null ) ? unparsedLvl.intValue() : 0;

                                    if (lvl > higherLvlIndex){
                                        higherLvlIndex = lvl;
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
                if (newLvl > higherLvlIndex){
                    notificationDatesToSend.add(notificationDate);
                }
            }

            if (!notificationDatesToSend.isEmpty()){
                updatedNotificationList.add(new NotificationPayloadDTO(pointId, notificationDatesToSend));
            }
        }

        try {
            firestoreService.updateNotifications(updatedNotificationList);
            //TODO continuar aqui, realizar envio de notificação
//            updatedNotificationList.stream().map(
//                    notificationPayloadDTO -> {
//                        String msg = notificationPayloadDTO.pointId() + " - ";
//                        NotificationMessageDTO messageDTO = new NotificationMessageDTO(notificationPayloadDTO.pointId(), );
//                        try {
//                            fcmService.sendNotification(messageDTO);
//                        } catch (FirebaseMessagingException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//            );
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private List<NotificationPayloadDTO> parseForecastsToNotification(List<ForecastUnitedDataDTO> payload) {
        List<NotificationPayloadDTO> response = new ArrayList<>();

        for (ForecastUnitedDataDTO forecast : payload) {
            String pointId = forecast.pointId();

            List<NotificationDateDTO> dateDTOS = new ArrayList<>();

            for (DailyModelDTO dailyDTO : forecast.forecasts()) {
                int valueOfRisk = checkForRisk(forecast.historyData().max(), forecast.historyData().min(), dailyDTO.riverDischarge());
                NotificationDateDTO dateDTO = new NotificationDateDTO(dailyDTO.date(), valueOfRisk);

                dateDTOS.add(dateDTO);
            }
            NotificationPayloadDTO notificationDate = new NotificationPayloadDTO(pointId, dateDTOS);

            response.add(notificationDate);
        }
        return response;
    }

    private int checkForRisk(Double hMax, Double hAvg, Double discharge) {
        final double t2 = hAvg * 2;
        final double t3 = hAvg * 3;
        final double hmax50p = hMax * 0.5;
        final double hmax75p = hMax * 0.75;

        if (discharge >= t2 && discharge < t3) {
            return 1;
        } else if (discharge >= t3) {
            return 2;
        } else if (hmax50p >= discharge && discharge < hmax75p) {
            return 3;
        } else {
            return 4;
        }
    }
}
