package gymtrack.auth;

public record AuthResponse(

		String accessToken,

		String refreshToken,

		String id,

		String userName,

		String picture

) {
}
