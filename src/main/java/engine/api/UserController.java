package engine.api;

import engine.dto.UserDto;
import engine.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "api/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "register")
    public ResponseEntity<UserDto> registration(@Valid @RequestBody UserDto userDto) {
        log.debug("Method POST 'registration' started with arg {}", userDto.getEmail());
        return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "authentic")
    public ResponseEntity<UserDto> authentication() {
        log.debug("Method GET 'authentication' started");
        UserDto userDto = userService.getCurrentUser();

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
}
