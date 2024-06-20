package ru.kata.spring.boot_security.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserErrorResponse;
import ru.kata.spring.boot_security.demo.exceptions.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.exceptions.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class AdminRestController {
    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/people")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getUsersList().stream().map(UserMapper::convertToUserDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        UserDTO userDTO = UserMapper.convertToUserDTO(userService.getUser(id));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id, HttpServletRequest request,
                                        HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = userService.getCurrentUser().getId();
        userService.delete(id);
        if (currentUserId.equals(id)) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/people")
    public ResponseEntity<Long> addUser(@RequestBody @Valid UserDTO userDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
                System.out.println(errorMsg);
                throw new UserNotCreatedException(errorMsg.toString());
            }
        }
        User user = UserMapper.convertToUser(userDto);
        userService.add(user);
        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

    @PatchMapping("/people")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserDTO userDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
                System.out.println(errorMsg);
                throw new UserNotCreatedException(errorMsg.toString());
            }
        }
        userService.update(UserMapper.convertToUser(userDto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/authUser")
    public UserDTO getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return UserMapper.convertToUserDTO(user);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException ignored) {
        UserErrorResponse response = new UserErrorResponse("Человек с таким айди не найден");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleNotCreatedException(UserNotCreatedException e) {
        UserErrorResponse response = new UserErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}