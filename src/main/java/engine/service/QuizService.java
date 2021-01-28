package engine.service;

import engine.dto.AnswerDto;
import engine.dto.QuizDto;
import engine.exception.QuizAlreadyExistException;
import engine.exception.ResourceNotFoundException;
import engine.model.CompletedQuiz;
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

    @Autowired
    private CompletedQuizService completedService;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    public QuizDto create(QuizDto quizDto) {
        log.debug("Method 'create' started with arg {}", quizDto);
        String title = quizDto.getTitle();

        if (quizRepository.findByTitle(title) != null) {
            String message = "Quiz " + title + " already exists";
            log.error(message);
            throw new QuizAlreadyExistException(message);
        }
        quizDto.setOwner(userService.getCurrentUser());
        Quiz entity = modelMapper.map(quizDto, Quiz.class);
        Quiz save = quizRepository.save(entity);
        QuizDto quiz = modelMapper.map(save, QuizDto.class);
        log.debug("Created successfully {}", quiz);
        return quiz;
    }

    public boolean solve(Integer quizId, AnswerDto answerDto) {
        log.debug("Method 'solve' started with args {}, {}", quizId, answerDto);
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> {
            String message = "Quiz " + quizId + " not found";
            log.error(message);
            throw new ResourceNotFoundException(message);
        });
        List<Integer> correctAnswer = quiz.getAnswers();
        List<Integer> verifyAnswer = answerDto.getAnswers();

        if (correctAnswer.containsAll(verifyAnswer) && verifyAnswer.containsAll(correctAnswer)) {
            completedService.add(new CompletedQuiz(), quizId);
            log.debug("Successfully completed quiz '{}'", quiz.getTitle());
            return true;
        }
        log.debug("Failed quiz '{}'", quiz.getTitle());
        return false;
    }

    public QuizDto getById(Integer id) {
        log.debug("Method 'getById' started with arg {}", id);
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> {
            String message = "Quiz " + id + "not found";
            log.error(message);
            throw new ResourceNotFoundException(message);
        });
        QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);
        log.debug("Quiz {} found successfully in DB", id);
        return quizDto;
    }

    public List<QuizDto> getAll(Integer pageNo) {
        log.debug("Method 'getAll' started with arg {}", pageNo);
        Page<Quiz> quizzes = quizRepository.findAll(PageRequest.of(pageNo, 10));

        return quizzes.stream().map(e -> modelMapper.map(e, QuizDto.class))
                .collect(Collectors.toList());
    }

    public boolean delete(Integer id) {
        log.debug("Method 'delete' started with arg {}", id);
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> {
            String message = "Quiz " + id + "not found";
            log.error(message);
            throw new ResourceNotFoundException(message);
        });
        Integer userId = userService.getCurrentUser().getId();

        if (quiz.getOwner().getId().equals(userId)) {
            quizRepository.delete(quiz);
            log.debug("Quiz {} deleted successfully from DB", id);
            return true;
        }
        log.debug("Failed to delete quiz {}", id);
        return false;
    }
}
