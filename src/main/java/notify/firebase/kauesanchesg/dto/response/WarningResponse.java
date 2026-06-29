package notify.firebase.kauesanchesg.dto.response;

import java.time.LocalDateTime;

public record WarningResponse(Long id, String message, LocalDateTime timestamp, String quota) {
}
