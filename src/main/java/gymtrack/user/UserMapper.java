package gymtrack.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserGetDTO map(UserEntity ent);

	UserEntity map(UserCreateDTO ent);
}
