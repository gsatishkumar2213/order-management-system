package order.management.system.paymentprocessingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobleExceptionHandler {
    @ExceptionHandler(InvalidinputException.class)
    public ResponseEntity<ErrorMessage> handlerInputRequest(InvalidinputException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                             body(new ErrorMessage(400, e.getMessage(), LocalDateTime.now()));
    }
}
