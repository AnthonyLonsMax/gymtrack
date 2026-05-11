package gymtrack.goal;

import jakarta.validation.constraints.Size;

public record GoalUpdateDTO(

		@Size(min = 4, max = 64) String name,

		String description,

		Boolean done

) {

}