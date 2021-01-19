package engine.service;

import engine.dto.UserDto;
import engine.model.User;
import engine.repository.UsersRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserService.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void createUser() {

        UserDto userDto = new UserDto();
        User entity = new User();
        Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(entity);
        Mockito.when(usersRepository.findByEmail(anyString())).thenReturn(null);
        User saved = new User();
        saved.setId(1);
        Mockito.when(usersRepository.save(entity)).thenReturn(saved);
        boolean actual = userService.createUser(userDto);

        Assert.assertTrue(actual);
    }

//    @Test
//    void getByEmail() {
//    }
}