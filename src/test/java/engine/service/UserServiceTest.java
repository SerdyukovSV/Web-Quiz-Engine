package engine.service;

import engine.dto.UserDto;
import engine.exception.UserAlreadyExistException;
import engine.model.User;
import engine.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserService.class)
class UserServiceTest {
    static Logger logger = Logger.getLogger(UserServiceTest.class.getName());

    @Autowired
    private UserService userService;
    @MockBean
    private UsersRepository usersRepository;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void create_Should_Return_SavedUser() {
        String email = "test@mail.com";
        String password = "123";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPassword("qwe");
        User entity = new User();

        when(usersRepository.findByEmail(email)).thenReturn(null);
        when(passwordEncoder.encode("qwe")).thenReturn(password);
        entity.setPassword(password);
        when(modelMapper.map(userDto, User.class)).thenReturn(entity);

        User saved = new User();
        saved.setId(1);
        saved.setPassword(password);
        when(usersRepository.save(entity)).thenReturn(saved);

        UserDto userRet = new UserDto();
        userRet.setId(1);
        userRet.setPassword(password);
        when(modelMapper.map(saved, UserDto.class)).thenReturn(userRet);

        UserDto actual = userService.create(userDto);
        assertEquals(actual, userRet);
    }

    @Test
    void create_Should_Return_Throw_Exception() {
        String email = "exist@mail.com";
        UserDto userDto = new UserDto();

        userDto.setEmail(email);
        when(usersRepository.findByEmail(email)).thenReturn(new User());

        Exception exception = assertThrows(UserAlreadyExistException.class,
                () -> userService.create(userDto));

        String actual = exception.getMessage();
        String expected = "User" + email + "already exists";

        assertEquals(expected, actual);
    }

    @Test
    void update_Should_Return_SavedUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@mail,com");
        User entity = new User();

        when(usersRepository.findByEmail(anyString())).thenReturn(entity);
        when(modelMapper.map(userDto, User.class)).thenReturn(entity);

        User userFormDb = new User();
        userFormDb.setId(1);
        when(usersRepository.save(entity)).thenReturn(userFormDb);

        UserDto userRet = new UserDto();
        userRet.setId(1);
        when(modelMapper.map(userFormDb, UserDto.class)).thenReturn(userRet);
        UserDto actual = userService.update(userDto);

        assertEquals(userRet, actual);
    }

    @Test
    void update_Should_Throw_Exception() {
        String email = "notExist@mail.com";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);

        when(usersRepository.findByEmail(anyString())).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class,
                () -> userService.update(userDto));

        String actual = exception.getMessage();
        String expected = "User " + email + " not found";

        assertEquals(expected, actual);
    }


    @Test
    void getByEmail_Should_Return_User() {
        String email = "exist@mail.com";
        User userFromDb = new User();
        UserDto userDto = new UserDto();

        when(usersRepository.findByEmail(anyString())).thenReturn(userFromDb);
        when(modelMapper.map(userFromDb, UserDto.class)).thenReturn(userDto);
        UserDto actual = userService.getByEmail(email);

        assertEquals(userDto, actual);
    }

    @Test
    void getByEmail_Should_Throw_Exception() {
        String email = "invalid@mail.com";

        when(usersRepository.findByEmail(anyString())).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class,
                () -> userService.getByEmail(email));
        assertEquals(exception.getMessage(),  "User " + email + " not found");
    }
}