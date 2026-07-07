package br.com.kasag.oraklast.utils;

import br.com.kasag.oraklast.dto.OpenMeteoResponseDTO;
import br.com.kasag.oraklast.dto.PointModelDTO;
import br.com.kasag.oraklast.dto.RTDBDailyModelDTO;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RTDBHashMapping {
    public static Map<String, Object> toHash(List<PointModelDTO> points, List<OpenMeteoResponseDTO> meteoResponse){
        Map<String, Object> payload = new HashMap<>();

        for (int i = 0; i < points.size(); i++) {
            PointModelDTO point = points.get(i);
            OpenMeteoResponseDTO meteoResponseDTO = meteoResponse.get(i);

            Map<String, Object> pointData = new HashMap<>();
            pointData.put("lat", point.latitude());
            pointData.put("lng", point.longitude());

            Map<String, Object> forecastMap = getMap(meteoResponseDTO);
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

            Double confiability = Math.floor((median / discharge * 100) * 100) / 100;

            RTDBDailyModelDTO dailyData = new RTDBDailyModelDTO(discharge, confiability);

            forecastMap.put(date, dailyData);
        }
        return forecastMap;
    }
}
