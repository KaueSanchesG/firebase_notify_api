package br.com.kasag.oraklast.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenMeteoDailyDTO(
        List<String> time,

        @JsonProperty("river_discharge")
        List<Double> riverDischarge,

        @JsonProperty("river_discharge_median")
        List<Double> riverDischargeMedian
) {
}
