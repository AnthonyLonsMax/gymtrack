package gymtrack.user;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserGetDTO(UUID id,

		@JsonProperty(value = "user_name") String userName,

		String picture,

		LocalDateTime created,

		LocalDateTime upated

) {

}
