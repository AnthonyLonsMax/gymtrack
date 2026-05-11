package gymtrack.workout;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkoutMapper {
	WorkoutGetDTO map(WorkoutEntity entity);

	WorkoutEntity map(WorkoutCreateDTO entity);
}
