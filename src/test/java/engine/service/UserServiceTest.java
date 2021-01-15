package engine.service;

import engine.dto.UserDto;
import engine.repository.UsersRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UsersRepository usersRepo;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void createUser() {
        UserDto userDto = new UserDto();

        userDto.setEmail("test@mail.com");
        userDto.setPassword("123");

        boolean isCreateUser = userService.createUser(userDto);

        Assert.assertTrue(isCreateUser);
    }

//    @Test
//    void completedQuizzes() {
//    }
//
//    @Test
//    void getCurrentUser() {
//    }
//
//    @Test
//    void convertToDto() {
//    }
//
//    @Test
//    void convertToEntity() {
//    }

}