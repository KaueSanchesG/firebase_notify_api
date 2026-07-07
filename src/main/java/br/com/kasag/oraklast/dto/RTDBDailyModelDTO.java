package br.com.kasag.oraklast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RTDBDailyModelDTO(
        @JsonProperty("river_discharge")
        Double riverDischarge,
        Double confiability
) {
}
