package engine.service;

import engine.dto.UserDto;
import engine.model.CompletedQuiz;
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

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User userFromDb = usersRepository.findByEmail(email);

        if (userFromDb == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return userFromDb;
    }

    public boolean save(User user) {
        if (usersRepository.findByEmail(user.getEmail()) != null) {
            return false;
        }
        usersRepository.save(user);
        return true;
    }

    public boolean create(UserDto userDto){
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        return save(convertToEntity(userDto));
    }

    public User getByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public List<CompletedQuiz> completedQuizzes() {
        return usersRepository.findByEmail(getCurrentUser().getEmail())
                .getCompletedQuizzes();
    }

    public UserDto convertToDto(User userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    public User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
