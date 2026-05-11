package gymtrack.auth;

import jakarta.annotation.Nonnull;

public record RefreshRequest(@Nonnull String refreshToken) {
}
