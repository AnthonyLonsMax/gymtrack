package gymtrack.workout;

import java.time.LocalDateTime;
import java.util.UUID;

public record WorkoutGetDTO(

		UUID id,

		String name,

		String picture,

		LocalDateTime created,

		LocalDateTime upated

) {

}
