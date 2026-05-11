package gymtrack.workout;

import jakarta.validation.constraints.Size;

public record WorkoutUpdateDTO(

		@Size(min = 4, max = 64) String name,

		@Size(min = 1) String picture

) {

}
