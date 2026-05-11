package gymtrack.gymday;

import java.time.LocalDateTime;
import java.util.UUID;

public record GymDayGetDTO(

		UUID id,

		LocalDateTime created,

		LocalDateTime upated

) {

}