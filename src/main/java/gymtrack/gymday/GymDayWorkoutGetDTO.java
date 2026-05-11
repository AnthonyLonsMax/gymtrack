package gymtrack.gymday;

import java.time.LocalDateTime;
import java.util.UUID;

public record GymDayWorkoutGetDTO(

		UUID id,

		String name,

		LocalDateTime created,

		LocalDateTime upated

) {

}