package br.com.kasag.oraklast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DailyModelDTO(
        String Data,
        @JsonProperty("river_discharge")
        Double riverDischarge,
        Double confiability
) {
}
