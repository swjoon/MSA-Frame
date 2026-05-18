package app.backend.itemservice.global.error.exception;

import org.springframework.http.HttpStatus;

public interface DomainErrorCode {

    HttpStatus getStatus();

    String getCode();

    String getMessage();

}
