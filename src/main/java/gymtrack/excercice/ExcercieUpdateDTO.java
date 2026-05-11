package gymtrack.excercice;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ExcercieUpdateDTO(

		@Size(min = 4, max = 46) String name,

		@Min(0) Integer sets,

		@Min(0) Integer reps,

		@Min(0) String weigth

) {

}
