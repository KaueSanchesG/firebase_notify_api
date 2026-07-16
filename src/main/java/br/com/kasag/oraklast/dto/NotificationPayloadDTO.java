package br.com.kasag.oraklast.dto;

import java.util.List;

public record NotificationPayloadDTO(String pointId, List<NotificationDateDTO> notificationInfo) {
}
