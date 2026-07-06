package br.com.kasag.oraklast.service;

import br.com.kasag.oraklast.dto.OpenMeteoResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

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
}
