package notify.firebase.kauesanchesg.dto.response;

import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

public record WarningResponse(Long id, String message, LocalDateTime timestamp, String quota, Point point) {
}
