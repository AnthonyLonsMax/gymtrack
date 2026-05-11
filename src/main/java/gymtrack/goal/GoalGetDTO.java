package gymtrack.goal;

import java.time.LocalDateTime;
import java.util.UUID;

public record GoalGetDTO(

		UUID id,

		String name,

		String description,

		Boolean done,

		LocalDateTime created,

		LocalDateTime upated

) {

}
