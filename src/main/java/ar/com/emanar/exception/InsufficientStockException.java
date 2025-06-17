package ar.com.emanar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class InsufficientStockException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InsufficientStockException(String message) {
		super(message);
	}	
}
