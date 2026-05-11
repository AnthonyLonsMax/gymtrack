package gymtrack.goal;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoalMapper {
	GoalGetDTO map(GoalEntity entity);

	GoalEntity map(GoalCreateDTO dto);
}