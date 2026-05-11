package gymtrack.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Size;

public record UserUpdateDTO(

		@JsonProperty(value = "user_name") @Size(min = 4, max = 68) String userName,

		@Size(min = 2, max = 68) String picture,

		@Size(min = 7, max = 128) String password) {
}
