package springboot.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.mail.MessagingException;

@RestControllerAdvice
public class ResourceGlobalException {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> HandlNotFoundException( ResourceNotFoundException e, WebRequest request) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND, e.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error, error.getHttpCode());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> HandlException(Exception e, WebRequest request) {
		ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request.getDescription(false));
		return  new ResponseEntity<>(error, error.getHttpCode());
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> HandlValueMissingException(Exception e, WebRequest request) {
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage(), request.getDescription(false));
		return  new ResponseEntity<>(error, error.getHttpCode());
	}

	@ExceptionHandler(MessagingException.class)
	public ResponseEntity<?> HandlValueMissingException(MessagingException e, WebRequest request) {
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage(), request.getDescription(false));
		return  new ResponseEntity<>(error, error.getHttpCode());
	}
}
