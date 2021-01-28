package engine.service;

import engine.dto.UserDto;
import engine.model.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.debug("Method 'loadUserByUsername' started with arg {}", email);
        UserDto userDto = userService.getByEmail(email);

        return modelMapper.map(userDto, User.class);
    }
}
