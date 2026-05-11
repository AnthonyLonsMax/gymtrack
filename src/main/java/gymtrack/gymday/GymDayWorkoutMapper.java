package gymtrack.gymday;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GymDayWorkoutMapper {
	GymDayWorkoutGetDTO map(GymDayWorkout entity);

	GymDayWorkout map(GymDayWorkoutCreateDTO dto);
}