package br.com.kasag.oraklast.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenMeteoResponseDTO(double latitude, double longitude, OpenMeteoDailyDTO daily) {
}
