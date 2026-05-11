package gymtrack.gymday;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record GymDayWorkoutExcerciceUpdateDTO(

		@Size(min = 4, max = 46) String name,

		@Min(0) Integer sets,

		@Min(0) Integer reps,

		String weigth

) {

}