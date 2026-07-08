package br.com.kasag.oraklast.service;

import br.com.kasag.oraklast.dto.OpenMeteoResponseDTO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenMeteoService {

    private final RestClient restClient;

    public OpenMeteoService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://flood-api.open-meteo.com/v1").build();
    }

    public List<OpenMeteoResponseDTO> doForecast(String latitude, String longitude) {
        return this.restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flood")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("daily", "river_discharge,river_discharge_median")
                        .queryParam("timezone", "America/Sao_Paulo")
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<OpenMeteoResponseDTO>>() {
                });
    }

    /**
     * Function 4 returning max-min-avg statistics of the points from the beginning of the century til last day of 2025.
     * Used 4 measures in
     */
    public void calculateHistoryOfThecentury(String lats, String lngs) {

        List<OpenMeteoResponseDTO> history = this.restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flood")
                        .queryParam("latitude", lats)
                        .queryParam("longitude", lngs)
                        .queryParam("daily", "river_discharge")
                        .queryParam("timezone", "America/Sao_Paulo")
                        .queryParam("start_date", "2000-01-01")
                        .queryParam("end_date", "2025-12-31")
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<OpenMeteoResponseDTO>>() {});

        for (int i = 0; i < history.size(); i++){

            OpenMeteoResponseDTO pointData = history.get(i);
            List<Double> historyDischarge = pointData.daily().riverDischarge();

            DoubleSummaryStatistics stats = historyDischarge.stream()
                    .filter(discharge -> discharge != null)
                    .mapToDouble(Double::doubleValue)
                    .summaryStatistics();

            double greaterOfAllTimes = stats.getMax();
            double minorOfAllTimes = stats.getMin();
            double historicalAvg = stats.getAverage();

            System.out.println(history.get(i).latitude() + " - " + history.get(i).longitude());
            System.out.println("Rio - Máxima Histórica: " + greaterOfAllTimes);
            System.out.println("Rio - Mínima Histórica: " + minorOfAllTimes);
            System.out.println("Rio - Média Normal: " + historicalAvg);
        }
    }
}
