package fr.formation.jeuxolympique.mappers;

import fr.formation.jeuxolympique.dto.SignUpDto;
import fr.formation.jeuxolympique.dto.UserDto;
import fr.formation.jeuxolympique.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
