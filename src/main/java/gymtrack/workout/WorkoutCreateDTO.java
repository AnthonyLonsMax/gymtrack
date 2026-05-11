package gymtrack.workout;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;

public record WorkoutCreateDTO(

		@Nonnull @Size(min = 4, max = 64) String name,

		@Nonnull @Size(min = 1) String picture

) {

}
