package gymtrack.objetives;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObjetiveMapper {
	ObjectiveGetDTO map(ObjetiveEntity entity);

	ObjetiveEntity map(ObjetiveCreateDTO dto);
}