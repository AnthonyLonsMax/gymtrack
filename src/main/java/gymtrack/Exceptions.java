package gymtrack;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class Exceptions {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public APIReponseError handleValidationException(
			MethodArgumentNotValidException r) {
		var errors = new HashMap<String, Object>();
		r.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new APIReponseError(ErrorCodes.VALIDATION_ERROR,
				"error validating the request data", errors);
	}

	@ExceptionHandler(APIException.class)
	public ResponseEntity<APIReponseError> handleAPIException(APIException r) {

		HttpStatus status = HttpStatus.BAD_REQUEST; // o 401, 403, 404, 500
													// según tu lógica
		return ResponseEntity.status(status).body(new APIReponseError(
				r.getCode(), r.getMessage(), r.getDetails()));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public APIReponseError handleHttpMessageNotReadable(
			HttpMessageNotReadableException r) {
		return new APIReponseError(ErrorCodes.REQUEST_ERROR, r.getMessage(),
				null);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public APIReponseError handleEntityNotFound(EntityNotFoundException r) {
		return new APIReponseError(ErrorCodes.NOT_FOUND, r.getMessage(), null);
	}

	@ExceptionHandler(EntityExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public APIReponseError handleEntityExists(EntityExistsException r) {
		return new APIReponseError(ErrorCodes.ALREADY_FOUND, r.getMessage(),
				null);
	}
}