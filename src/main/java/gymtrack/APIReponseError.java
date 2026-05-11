package gymtrack;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIReponseError {

	private ErrorCodes code;

	private String message;

	private Map<String, Object> details;

	public static APIReponseError of(ErrorCodes code, String messsage) {
		return new APIReponseError(code, messsage, null);
	}

	public static APIReponseError of(ErrorCodes code, String messsage,
			Object err) {
		return new APIReponseError(code, messsage, Map.of("error", err));
	}
}
