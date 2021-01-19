package engine.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import engine.dto.UserDto;
import engine.repository.UsersRepository;
import engine.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
//@EnableAutoConfiguration(exclude = SecurityConfiguration.class)
class UserControllerTest {

//    private Logger LOG = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private MockMvc mvc;

//    @MockBean
//    private UserController userController;

    @MockBean
    private UserService userService;

    @MockBean
    private UsersRepository usersRepository;


//    @BeforeEach
//    public void setUp() {
//        UserDto firstUser = new UserDto(1, "Mickey", "123");
//        UserDto secondUser = new UserDto(2, "Frankie", "123");
//        UserDto thirdUser = new UserDto(3, "Tommy", "123");
//
//        Mockito.when(userService.createUser(firstUser)).thenReturn(true);
//        Mockito.when(userService.createUser(secondUser)).thenReturn(true);
//        Mockito.when(userService.createUser(thirdUser)).thenReturn(true);
//
//    }

    @Test
    void registrationUser() throws Exception {
        UserDto firstUser = new UserDto(1, "Mickey", "12345");
//        UserDto secondUser = new UserDto(2, "Frankie", "123");
//        UserDto thirdUser = new UserDto(3, "Tommy", "123");

        ObjectMapper objectMapper = new ObjectMapper();

        given(userService.createUser(firstUser)).willReturn(true);
        given(userService.getByEmail(firstUser.getEmail())).willReturn(firstUser);

        mvc.perform(post("api/register")
                .secure(false)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstUser)))
                .andExpect(status().isCreated());
//                .andExpect(jsonPath("email", is(firstUser.getEmail())));



    }

//    @Test
//    void authenticateUser() {
//    }
}