package app.backend.paymentservice.global.error.exception;

import org.springframework.http.HttpStatus;

public interface DomainErrorCode {

    HttpStatus getStatus();

    String getCode();

    String getMessage();

}
