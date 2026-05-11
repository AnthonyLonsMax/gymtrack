package gymtrack.auth;

import jakarta.annotation.Nonnull;

public record LoginRequest(
		@Nonnull String userName,
		@Nonnull String password) {
}
