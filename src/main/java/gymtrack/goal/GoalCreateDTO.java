package gymtrack.goal;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;

public record GoalCreateDTO(

		@Nonnull @Size(min = 4, max = 64) String name,

		String description,

		@Nonnull Boolean done

) {

}