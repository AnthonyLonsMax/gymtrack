package gymtrack;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIException extends RuntimeException {

	private ErrorCodes code;

	private String sourceMessage;
	private Map<String, Object> details;

	public APIException(ErrorCodes code, String message) {
		super(message);
		this.code = code;
		this.sourceMessage = message;
	}

	public APIException(ErrorCodes code, String message,
			Map<String, Object> details) {
		super(message);
		this.code = code;
		this.sourceMessage = message;
		this.details = details;
	}

	public static APIException of(ErrorCodes code, String message) {
		return new APIException(code, message);
	}

}
