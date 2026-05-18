package app.backend.itemservice.item.exception;

import app.backend.itemservice.global.error.exception.DomainErrorCode;
import app.backend.itemservice.global.error.exception.DomainException;

public class ItemException extends DomainException {
	public ItemException(DomainErrorCode errorCode) {
		super(errorCode);
	}
}
