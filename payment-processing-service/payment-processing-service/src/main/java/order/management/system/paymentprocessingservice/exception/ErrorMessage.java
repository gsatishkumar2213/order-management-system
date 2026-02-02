package order.management.system.paymentprocessingservice.exception;

import java.time.LocalDateTime;

public record ErrorMessage(int httpCode, String description, LocalDateTime timestamp) {
}
