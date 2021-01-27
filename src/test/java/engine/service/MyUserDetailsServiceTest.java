package engine.service;

import engine.dto.UserDto;
import engine.model.User;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = MyUserDetailsService.class)
class MyUserDetailsServiceTest {

    @Autowired
    private MyUserDetailsService userDetailsService;
    @MockBean
    private UserService userService;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    void loadUserByUsername_Should_Return_UserEntity() {
        UserDto userDto = new UserDto();
        User entity = new User();

        when(userService.getByEmail("test@mail.com")).thenReturn(userDto);
        when(modelMapper.map(userDto, User.class)).thenReturn(entity);

        UserDetails userRet = userDetailsService
                .loadUserByUsername("test@mail.com");

        assertEquals(entity, userRet);
    }
}