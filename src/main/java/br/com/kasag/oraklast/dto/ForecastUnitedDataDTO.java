package br.com.kasag.oraklast.dto;

import java.util.List;

public record ForecastUnitedDataDTO(String pointId, Double lat, Double lng, HistoryDataDTO historyData, List<DailyModelDTO> forecasts) {
}
