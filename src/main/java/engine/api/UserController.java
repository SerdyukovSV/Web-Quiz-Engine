package engine.api;

import engine.dto.UserDto;
import engine.exception.UserAlreadyExistException;
import engine.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "api/")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @PostMapping(path = "register")
    public ResponseEntity<UserDto> registrationUser(@Valid @RequestBody UserDto userDto) {
        if (!userService.createUser(userDto)) {
            throw new UserAlreadyExistException();
        }
        return new ResponseEntity<>(userService.getByEmail(userDto.getEmail()), HttpStatus.CREATED);
    }

    @GetMapping(path = "authentic")
    public ResponseEntity<UserDto> authenticationUser() {
        UserDto userDto = modelMapper.map(userService.getCurrentUser(), UserDto.class);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
}
