package gymtrack.objetives;

import java.time.LocalDateTime;
import java.util.UUID;

public record ObjectiveGetDTO(

		UUID id,

		String name,

		String description,

		LocalDateTime created,

		LocalDateTime upated

) {

}
