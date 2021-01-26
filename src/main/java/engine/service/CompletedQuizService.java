package engine.service;

import engine.dto.CompletedQuizDto;
import engine.model.CompletedQuiz;
import engine.repository.CompletedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompletedQuizService {

    private final UserService userService;
    private final CompletedRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public CompletedQuizService(UserService userService, CompletedRepository repository, ModelMapper modelMapper) {
        this.userService = userService;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public void addCompletedQuiz(Integer quizId) {
        CompletedQuiz completedQuiz = new CompletedQuiz();
        Integer userId = userService.getCurrentUser().getId();

        completedQuiz.setUserId(userId);
        completedQuiz.setQuizId(quizId);
        repository.save(completedQuiz);
    }

    public List<CompletedQuizDto> getCompletedQuizzes() {
        Integer userId = userService.getCurrentUser().getId();

        return repository.findByUserIdOrderByCompletedAtDesc(userId)
                .stream().map(e -> modelMapper.map(e, CompletedQuizDto.class))
                .collect(Collectors.toList());
    }
}
