package gymtrack.gymday;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record GymDayExcerciceDoneCreateDTO(

		@Nonnull @Size(min = 4, max = 46) String name,

		@Min(0) @Nonnull Integer sets,

		@Min(0) @Nonnull Integer reps,

		String weigth

) {

}