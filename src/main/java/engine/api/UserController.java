package engine.api;

import engine.dto.UserDto;
import engine.repository.UsersRepository;
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

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping(path = "register")
    public ResponseEntity<UserDto> registration(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "authentic")
    public ResponseEntity<UserDto> authentication() {
        UserDto userDto = userService.getCurrentUser();

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
}
