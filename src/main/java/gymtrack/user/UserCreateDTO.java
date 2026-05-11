package gymtrack.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(

		@Nonnull @Size(min = 4, max = 68) @JsonProperty("user_name") String userName,

		@Nonnull @Size(min = 2, max = 68) String picture,

		@Nonnull @Size(min = 7, max = 128) String password) {
}
