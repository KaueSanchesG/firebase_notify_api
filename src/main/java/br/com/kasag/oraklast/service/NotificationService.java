package br.com.kasag.oraklast.service;

import br.com.kasag.oraklast.dto.NotificationMessageDTO;
import br.com.kasag.oraklast.dto.NotificationValidationDTO;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private static FCMService fcmService;

    /**
     * Method to validate how far for the point's max history value is the forecasted discharge
     */

    /**
     * Quero ter aqui 2 tipos diferentes de mensagens:
     *  1° por valor próximo a maxima histórica
     *  2° por valor muito a cima do normal
     */
    public static void checkForOvercharge(NotificationValidationDTO dto){

        double percentageOfHistoryMax = dto.discharge()/dto.hMax();

    };

    private static void checkTimesItsValue(NotificationValidationDTO dto) throws FirebaseMessagingException {
        final double timesTwo = dto.hAvg()*2;
        final double timesTree = dto.hAvg()*3;

        if (dto.discharge() >= timesTwo && dto.discharge() < timesTree) fcmService.sendNotification(new NotificationMessageDTO(dto.pName() + "t2", "Dobro do volume", ""));
    }
}
