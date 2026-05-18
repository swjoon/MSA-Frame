package app.backend.itemservice.global.error.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final DomainErrorCode domainErrorCode;

    public DomainException(DomainErrorCode domainErrorCode) {
        super(domainErrorCode.getMessage());
        this.domainErrorCode = domainErrorCode;
    }

}
