package engine.service;

import engine.dto.UserDto;
import engine.model.User;
import engine.repository.UsersRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

//    @TestConfiguration
//    static class UserServiceTestContextConfiguration {
//        @Bean
//        public UserService userService() {
//            return new UserService();
//        }
//    }

    @Autowired
    private UserService userService;
    @MockBean
    private UsersRepository usersRepository;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setUp(){
        User user = new User(1, "test@mail.com", "password", null);

        Mockito.when(usersRepository.findByEmail(user.getEmail())).thenReturn(user);
    }

    @Test
    public void create() {
//        UserDto userDto = new UserDto(1, "test@mail.com", "password");
//        User user = new User(1, "test@mail.com", "password", null);

//        Mockito.when(usersRepository.findByEmail("test@mail.com"))
//                .thenReturn(user);

//        Assert.assertTrue(userService.createUser(userDto));
//        User userFromDb = usersRepository.findByEmail("test@mail.com");
//        Assert.assertNotNull(userFromDb);
//        Assert.assertEquals();

        String email = "test@mail.com";
        User found = userService.getByEmail(email);

        Assert.assertThat(found.getEmail(), Matchers.equalTo(email));



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