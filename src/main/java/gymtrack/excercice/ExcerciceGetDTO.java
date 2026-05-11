package gymtrack.excercice;

import java.time.LocalDateTime;
import java.util.UUID;

public record ExcerciceGetDTO(

		UUID id,

		String name,

		Integer sets,

		Integer reps,

		String weigth,

		LocalDateTime created,

		LocalDateTime upated

) {

}
