package gymtrack.excercice;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ExcerciceCreateDTO(

		@Size(min = 4, max = 46) @Nonnull String name,

		@Min(0) @Nonnull Integer sets,

		@Min(0) @Nonnull Integer reps,

		@Nonnull String weigth

) {

}
