package br.com.kasag.oraklast.service;

import br.com.kasag.oraklast.dto.*;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SyncService {

    @Autowired
    private FirestoreService firestoreService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OpenMeteoService meteoService;

    /**
     * Points around triple border among Brasil, Paraguay, Argentina
     *
     * @content pN stands for Paraguay points
     * @content BN stands for Brasil points
     * @content aN stands for Argentina points
     * <p>
     * hMax-hMin-hAvg values were obtained once with meteoService.calculateHistoryOfThecentury(lats, lngs) function,
     * some values must be wrong due to the geoPoint being too close to land or some point where the external API's
     * considers land
     */
    private final List<PointModelDTO> points = List.of(
            new PointModelDTO("p1", -25.44873110009506, -54.64848707106452, 32808.52, 3426.68, 9532.43),
            new PointModelDTO("p2", -25.46604073933723, -54.66875376662977, 32808.52, 3426.68, 9533.54),
            new PointModelDTO("p3", -25.45214016036795, -54.67424932091286, 32808.52, 3426.68, 9533.54),
            new PointModelDTO("p4", -25.41529268908153, -54.68116785986069, 32808.52, 3426.68, 9533.02),
            new PointModelDTO("p5", -25.40715364143828, -54.68480172394786, 32808.52, 3426.68, 9533.02),
            new PointModelDTO("p6", -25.41040254753702, -54.69399177757399, 32808.52, 3426.68, 9533.02),
            new PointModelDTO("b1", -25.47766237391046, -54.60401165565593, 21.08, 0.05, 1.34),
            new PointModelDTO("b2", -25.49662535912696, -54.61877065024706, 21.08, 0.05, 1.34),
            new PointModelDTO("b3", -25.53541957371173, -54.62202404347496, 32808.52, 3426.68, 9535.72),
            new PointModelDTO("b4", -25.58028211779522, -54.58896119364636, 30.62, 0.0, 2.41),
            new PointModelDTO("a1", -25.89848504820023, -54.54347205392787, 557.47, 0.0, 57.48),
            new PointModelDTO("a2", -25.6059442462042, -54.58227626429527, 17099.15, 411.10, 1468.48),
            new PointModelDTO("a3", -25.6562983474533, -54.58097986624939, 17.32, 0.0, 1.78)
    );

    private final String lats = points.stream()
            .map(p -> String.valueOf(p.latitude()))
            .collect(Collectors.joining(","));

    private final String lngs = points.stream()
            .map(p -> String.valueOf(p.longitude()))
            .collect(Collectors.joining(","));

    public void emitSyncEvent() {

        List<OpenMeteoResponseDTO> meteoResponse = meteoService.doForecast(lats, lngs);

        List<List<DailyModelDTO>> dailyMapper = meteoResponse.stream()
                .map(response -> {
                    var daily = response.daily();
                    return IntStream.range(0, daily.time().size())
                            .mapToObj(i -> {
                                String date = daily.time().get(i);
                                Double discharge = daily.riverDischarge().get(i);
                                Double trustability = calcTrustability(discharge, daily.riverDischargeMedian().get(i));

                                return new DailyModelDTO(date, discharge, trustability);
                            }).toList();
                }).toList();

        List<ForecastUnitedDataDTO> payload = getForecastUnitedDataDTOS(dailyMapper);

        try{
            firestoreService.updatePoints(payload);
            notificationService.doNotificationsRoutine(payload);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private @NonNull List<ForecastUnitedDataDTO> getForecastUnitedDataDTOS(List<List<DailyModelDTO>> dailyMapper) {
        List<ForecastUnitedDataDTO> payload = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            ForecastUnitedDataDTO dto = new ForecastUnitedDataDTO(
                    points.get(i).id(),
                    points.get(i).latitude(),
                    points.get(i).longitude(),
                    new HistoryDataDTO(
                            points.get(i).historyMax(),
                            points.get(i).historyMin(),
                            points.get(i).historyAvg()
                    ),
                    dailyMapper.get(i)
                    );
            payload.add(dto);
        }
        return payload;
    }

    private double calcTrustability(double discharge, double median){
        if (discharge == 0.0 && median == 0.0) return 100.0;
        double minorValue = Math.min(discharge, median);
        double greaterValue = Math.max(discharge, median);

        double ratio = minorValue/greaterValue;

        return Math.floor((ratio * 100) * 100) / 100;
    }

    /**
     * Used 4 history data metrics only (and once while developing)
     */
//    public void calcHistMetrics() {
//        meteoService.calculateHistoryOfThecentury(lats, lngs);
//    }

}
