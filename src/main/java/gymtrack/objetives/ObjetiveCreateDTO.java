package gymtrack.objetives;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;

public record ObjetiveCreateDTO(

		@Nonnull @Size(min = 4, max = 64) String name,

		String description

) {

}