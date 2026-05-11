package gymtrack.gymday;

import jakarta.validation.constraints.Size;

public record GymDayWorkoutUpdateDTO(

		@Size(min = 1, max = 64) String name

) {

}