package engine.service;

import engine.dto.CompletedQuizDto;
import engine.model.CompletedQuiz;
import engine.repository.CompletedRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CompletedQuizService {

    @Autowired
    private UserService userService;
    @Autowired
    private CompletedRepository repository;
    @Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    public CompletedQuizService(UserService userService, CompletedRepository repository, ModelMapper modelMapper) {
//        this.userService = userService;
//        this.repository = repository;
//        this.modelMapper = modelMapper;
//    }

    public void add(CompletedQuiz completedQuiz, Integer quizId) {
        log.debug("Method 'add' started with arg {}, {}", completedQuiz, quizId);
        Integer userId = userService.getCurrentUser().getId();

        completedQuiz.setUserId(userId);
        completedQuiz.setQuizId(quizId);
        repository.save(completedQuiz);
        log.debug("Added new entry completed quiz {}", completedQuiz);
    }

    public List<CompletedQuizDto> getAll() {
        log.debug("Method 'getAll' started");
        Integer userId = userService.getCurrentUser().getId();

        return repository.findAllByUserIdOrderByCompletedAtDesc(userId).stream()
                .map(e -> modelMapper.map(e, CompletedQuizDto.class))
                .collect(Collectors.toList());
    }
}
