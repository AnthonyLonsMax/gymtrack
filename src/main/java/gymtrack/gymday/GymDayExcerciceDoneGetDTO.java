package gymtrack.gymday;

import java.time.LocalDateTime;
import java.util.UUID;

public record GymDayExcerciceDoneGetDTO(

		UUID id,

		String name,

		Integer sets,

		Integer reps,

		String weigth,

		LocalDateTime created,

		LocalDateTime upated

) {

}