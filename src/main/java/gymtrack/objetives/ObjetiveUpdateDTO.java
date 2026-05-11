package gymtrack.objetives;

import jakarta.validation.constraints.Size;

public record ObjetiveUpdateDTO(

		@Size(min = 4, max = 64) String name,

		String description

) {

}