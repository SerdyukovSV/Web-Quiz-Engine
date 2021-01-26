package engine.service;

import engine.dto.QuizDto;
import engine.exception.QuizAlreadyExistException;
import engine.exception.ResourceNotFoundException;
import engine.dto.AnswerDto;
import engine.model.Quiz;
import engine.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuizService {

    private final CompletedQuizService completedService;
    private final QuizRepository quizRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public QuizService(CompletedQuizService completedService, QuizRepository quizRepository, UserService userService, ModelMapper modelMapper) {
        this.completedService = completedService;
        this.quizRepository = quizRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public QuizDto create(QuizDto quizDto) {
        String title = quizDto.getTitle();

        if (quizRepository.findByTitle(title) != null) {
            throw new QuizAlreadyExistException("Quiz " + title + " already exists");
        }
        quizDto.setOwner(userService.getCurrentUser());
        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        Quiz save = quizRepository.save(quiz);
        return modelMapper.map(save, QuizDto.class);
    }

    public boolean solve(Integer quizId, AnswerDto answerDto) {
        log.debug("start method solve whit parameters quizId {}, answerDto {}", quizId, answerDto);
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> {
            String message = "Quiz" + quizId + " not found";
            log.error(message);
            throw new ResourceNotFoundException(message);
        });
        List<Integer> correctAnswer = quiz.getAnswers();
        List<Integer> verifyAnswer = answerDto.getAnswers();

        if (correctAnswer.containsAll(verifyAnswer) && verifyAnswer.containsAll(correctAnswer)
                /*&& (correctAnswer.size() == verifyAnswer.size())*/) {
            completedService.addCompletedQuiz(quizId);
            return true;
        }
        return false;
    }

    public QuizDto getById(Integer id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(quiz, QuizDto.class);
    }

    public List<QuizDto> getAll(Integer pageNo) {
        Page<Quiz> quizzes = quizRepository.findAll(PageRequest.of(pageNo, 10));

        return quizzes.stream().map(e -> modelMapper.map(e, QuizDto.class))
                .collect(Collectors.toList());
    }

    public boolean delete(Integer id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        Integer userId = userService.getCurrentUser().getId();

        if (quiz.getOwner().getId().equals(userId)) {
            quizRepository.delete(quiz);
            return true;
        }
        return false;
    }
}
