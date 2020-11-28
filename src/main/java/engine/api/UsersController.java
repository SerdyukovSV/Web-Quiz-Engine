package engine.api;

import engine.exception.UserAlreadyExistException;
import engine.model.User;
import engine.service.AuthenticationUsers;
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

    @Autowired
    AuthenticationUsers authUser;

    @PostMapping(path = "/api/register")
    public ResponseEntity addUser(@Valid @RequestBody User user) throws UserAlreadyExistException {
        if (!userService.saveUser(user)) {
            throw new UserAlreadyExistException();
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @GetMapping(path = "/api/user")
    public String currentUserName() {
        User user = authUser.getAuthenticationUsers();
        return "email: " + user.getEmail() + "\nuserID: " + user.getId();
    }
}
