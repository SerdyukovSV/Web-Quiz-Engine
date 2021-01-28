package engine.service;

import engine.dto.UserDto;
import engine.exception.UserAlreadyExistException;
import engine.model.User;
import engine.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDto create(UserDto userDto) {
        log.debug("Method 'create' started with arg {}", userDto.getEmail());
        String email = userDto.getEmail();

        if (usersRepository.findByEmail(email) != null) {
            String message = "User " + email + " already exists";
            log.error(message);
            throw new UserAlreadyExistException(message);
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User save = usersRepository.save(modelMapper.map(userDto, User.class));
        UserDto user = modelMapper.map(save, UserDto.class);
        log.debug("Created successfully {}", user.getEmail());
        return user;
    }

    public UserDto update(UserDto userDto) {
        log.debug("Method 'update' started with arg {}", userDto.getEmail());
        String email = userDto.getEmail();

        if (usersRepository.findByEmail(email) == null) {
            String message = "User " + email + " not found";
            log.error(message);
            throw new UsernameNotFoundException(message);
        }
        User save = usersRepository.save(modelMapper.map(userDto, User.class));
        UserDto user = modelMapper.map(save, UserDto.class);
        log.debug("Updated successfully {}", user.getEmail());
        return user;
    }

    public UserDto getByEmail(String email) {
        log.debug("Method 'getByEmail' started with arg {}", email);
        User user = usersRepository.findByEmail(email);

        if (user == null) {
            String message = "User " + email + " not found";
            log.error(message);
            throw new UsernameNotFoundException(message);
        }
        UserDto userDto = modelMapper.map(user, UserDto.class);
        log.debug("User {} found successfully in DB", email);
        return userDto;
    }

    public UserDto getCurrentUser() {
        log.debug("Method 'getCurrentUser' started");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        log.debug("Current session user {}", userDto.getEmail());
        return userDto;
    }
}
