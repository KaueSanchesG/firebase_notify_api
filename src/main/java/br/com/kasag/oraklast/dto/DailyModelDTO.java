package br.com.kasag.oraklast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DailyModelDTO(
        String date,
        @JsonProperty("river_discharge")
        Double riverDischarge,
        Double trustability
) {
}
