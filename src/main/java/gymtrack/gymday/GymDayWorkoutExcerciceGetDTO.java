package gymtrack.gymday;

import java.time.LocalDateTime;
import java.util.UUID;

public record GymDayWorkoutExcerciceGetDTO(

		UUID id,

		String name,

		Integer sets,

		Integer reps,

		String weigth,

		LocalDateTime created,

		LocalDateTime upated

) {

}