package gymtrack.gymday;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GymDayMapper {
	GymDayGetDTO map(GymDayEntity entity);
}