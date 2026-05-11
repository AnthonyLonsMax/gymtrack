package gymtrack.gymday;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GymDayWorkoutExcerciceMapper {
	GymDayWorkoutExcerciceGetDTO map(GymDayWorkoutExcercice entity);

	GymDayWorkoutExcercice map(GymDayWorkoutExcerciceCreateDTO dto);
}