package gymtrack.auth;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
		@Nonnull @Size(min = 4, max = 68) String userName,
		@Nonnull @Size(min = 2, max = 68) String picture,
		@Nonnull @Size(min = 7, max = 128) String password) {
}
