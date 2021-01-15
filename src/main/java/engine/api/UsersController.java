package engine.api;

import engine.dto.UserDto;
import engine.exception.UserAlreadyExistException;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UsersController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/api/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        if (!userService.saveUser(userDto)) {
            throw new UserAlreadyExistException();
        }
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/api/authentic")
    public ResponseEntity<UserDto> authenticateUser() {
        UserDto user = userService.convertToDto(userService.getCurrentUser());
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
}
