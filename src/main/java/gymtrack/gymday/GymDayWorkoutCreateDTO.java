package gymtrack.gymday;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;

public record GymDayWorkoutCreateDTO(

		@Nonnull @Size(min = 1, max = 64) String name

) {

}