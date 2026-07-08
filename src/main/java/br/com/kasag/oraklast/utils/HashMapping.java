package br.com.kasag.oraklast.utils;

import br.com.kasag.oraklast.dto.NotificationValidationDTO;
import br.com.kasag.oraklast.dto.OpenMeteoResponseDTO;
import br.com.kasag.oraklast.dto.PointModelDTO;
import br.com.kasag.oraklast.dto.DailyModelDTO;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.kasag.oraklast.service.NotificationService.checkForOvercharge;

public class HashMapping {


    public static Map<String, Object> toHash(List<PointModelDTO> points, List<OpenMeteoResponseDTO> meteoResponse){
        Map<String, Object> payload = new HashMap<>();

        for (int i = 0; i < points.size(); i++) {
            PointModelDTO point = points.get(i);
            OpenMeteoResponseDTO meteoResponseDTO = meteoResponse.get(i);

            Map<String, Object> pointData = new HashMap<>();
            pointData.put("lat", point.latitude());
            pointData.put("lng", point.longitude());
            pointData.put("hMax", point.historyMax());
            pointData.put("hMin", point.historyMin());
            pointData.put("hAvh", point.historyAvg());

            Map<String, Object> forecastMap = new HashMap<>();

            List<String> dates = meteoResponseDTO.daily().time();
            List<Double> discharges = meteoResponseDTO.daily().riverDischarge();
            List<Double> dischargesMedian = meteoResponseDTO.daily().riverDischargeMedian();

            for (int dayIndex = 0; dayIndex < dates.size(); dayIndex++) {
                String date = dates.get(dayIndex);
                Double discharge = discharges.get(dayIndex);
                Double median = dischargesMedian.get(dayIndex);

                Double trustability = calcConfiability(median, discharge);

                /**
                 * Checks if it is trustable before checking for overcharges
                 */
                if (trustability > 99.5) checkForOvercharge(new NotificationValidationDTO(point.id(), point.historyMax(), point.historyAvg(), discharge));

                DailyModelDTO dailyData = new DailyModelDTO(discharge, trustability);

                forecastMap.put(date, dailyData);
            }

            pointData.put("forecasts", forecastMap);

            payload.put(point.id(), pointData);
        }
        return payload;
    }

    private static @NonNull Map<String, Object> getMap(OpenMeteoResponseDTO meteoResponseDTO) {
        Map<String, Object> forecastMap = new HashMap<>();

        List<String> dates = meteoResponseDTO.daily().time();
        List<Double> discharges = meteoResponseDTO.daily().riverDischarge();
        List<Double> dischargesMedian = meteoResponseDTO.daily().riverDischargeMedian();

        for (int dayIndex = 0; dayIndex < dates.size(); dayIndex++) {
            String date = dates.get(dayIndex);
            Double discharge = discharges.get(dayIndex);
            Double median = dischargesMedian.get(dayIndex);

            Double confiability = calcConfiability(median, discharge);

            DailyModelDTO dailyData = new DailyModelDTO(discharge, confiability);

            forecastMap.put(date, dailyData);
        }
        return forecastMap;
    }

    private static double calcConfiability(double discharge, double median){
        if (discharge == 0.0 && median == 0.0) return 1000;
        double minorValue = Math.min(discharge, median);
        double greaterValue = Math.max(discharge, median);

        double ratio = minorValue/greaterValue;

        return Math.floor((ratio * 100) * 100) / 100;
    }
}
