package engine.service;

import engine.dto.UserDto;
import engine.model.User;
import engine.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User userFromDb = usersRepository.findByEmail(email);

        if (userFromDb == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return userFromDb;
    }

    public boolean createUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (usersRepository.findByEmail(user.getEmail()) != null) {
            return false;
        }
        User save = usersRepository.save(user);
        return save.getId() != null;
    }

    public boolean updateUser(User user) {
        User save = usersRepository.save(user);

        return !save.equals(user);
    }

    public UserDto getByEmail(String email) {
        User user = usersRepository.findByEmail(email);

        return modelMapper.map(user, UserDto.class);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
