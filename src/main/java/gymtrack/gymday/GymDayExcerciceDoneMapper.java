package gymtrack.gymday;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GymDayExcerciceDoneMapper {
	GymDayExcerciceDoneGetDTO map(GymDayExcerciceDone entity);

	GymDayExcerciceDone map(GymDayExcerciceDoneCreateDTO dto);
}