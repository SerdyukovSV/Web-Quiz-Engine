package engine.service;

import engine.dto.QuizDto;
import engine.exception.ResourceNotFoundException;
import engine.model.Answer;
import engine.model.Quiz;
import engine.model.User;
import engine.repository.QuizRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public QuizDto createQuiz(QuizDto quizDto) {
        Quiz quiz = modelMapper.map(quizDto, Quiz.class);

        quiz.setOwner(userService.getCurrentUser());
        quizRepository.save(quiz);
        return modelMapper.map(quiz, QuizDto.class);
    }

    public boolean solveQuiz(Integer quizId, Answer answer) throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(ResourceNotFoundException::new);

        if (quiz.getAnswers().equals(answer.getAnswers())){
            return completedService.addCompletedQuiz(quizId);
        }
        return false;
    }

    public QuizDto getQuizById(Integer id) throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(quiz, QuizDto.class);
    }

    public List<Quiz> getAllQuizzes(Integer pageNo) {
        return quizRepository.findAll(PageRequest.of(pageNo, 10)).getContent();
    }

    public boolean deleteQuiz(Integer id) throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        User user = userService.getCurrentUser();

        if (quiz.getOwner().getId().equals(user.getId())) {
            quizRepository.delete(quiz);
            return true;
        }
        return false;
    }
}
