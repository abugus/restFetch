package ru.kata.spring.boot_security.demo.mapper;

import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static User convertToUser(UserDTO userDto) {
        Set<Role> roleSet = userDto.getRoleSet().stream().map(Role::getRole).collect(Collectors.toSet());
        return new User(userDto.getId(), userDto.getUsername(), userDto.getPass(), roleSet);
    }

    public static UserDTO convertToUserDTO(User user) {
        Set<String> roleSet = user.getRoleSet().stream().map(Role::getValue).collect(Collectors.toSet());
        return new UserDTO(user.getId(), user.getUsername(), user.getPass(), roleSet);
    }
}
