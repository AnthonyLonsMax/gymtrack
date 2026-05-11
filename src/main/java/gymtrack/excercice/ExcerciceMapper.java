package gymtrack.excercice;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExcerciceMapper {
	ExcerciceGetDTO map(ExcercieEntity entity);

	ExcercieEntity map(ExcerciceCreateDTO dto);
}