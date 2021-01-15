package engine.api;

import engine.dto.UserDto;
import engine.exception.UserAlreadyExistException;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path = "register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        if (!userService.createUser(userDto)) {
            throw new UserAlreadyExistException();
        }
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "authentic")
    public ResponseEntity<UserDto> authenticateUser() {
        UserDto userDto = userService.convertToDto(userService.getCurrentUser());

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
}
