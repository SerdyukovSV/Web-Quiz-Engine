package engine.service;

import engine.dto.UserDto;
import engine.exception.UserAlreadyExistException;
import engine.model.User;
import engine.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDto create(UserDto userDto) {
        String email = userDto.getEmail();

        if (usersRepository.findByEmail(email) != null) {
            throw new UserAlreadyExistException("User " + email + " already exists");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User save = usersRepository.save(modelMapper.map(userDto, User.class));
        return modelMapper.map(save, UserDto.class);
    }

    public UserDto update(UserDto userDto) {
        String email = userDto.getEmail();

        if (usersRepository.findByEmail(email) == null) {
            throw new UsernameNotFoundException("User " + email + " not found");
        }
        User save = usersRepository.save(modelMapper.map(userDto, User.class));
        return modelMapper.map(save, UserDto.class);
    }

    public UserDto getByEmail(String email) {
        User user = usersRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " not found");
        }
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelMapper.map(user, UserDto.class);
    }
}
