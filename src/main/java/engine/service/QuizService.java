package engine.service;

import engine.dto.QuizDto;
import engine.exception.ResourceNotFoundException;
import engine.model.Answer;
import engine.model.CompletedQuiz;
import engine.model.Quiz;
import engine.model.User;
import engine.repository.CompletedRepository;
import engine.repository.QuizRepository;
import engine.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    CompletedRepository completedRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    public QuizDto createQuiz(QuizDto quizDto) {
        Quiz quiz = convertToEntity(quizDto);
        quiz.setOwner(userService.getCurrentUser());
        quizRepository.save(quiz);
        return convertToDto(quiz);
    }

    public boolean solveQuiz(Integer id, Answer answer) throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (Arrays.equals(answer.getAnswers(), quiz.getAnswer())) {
            CompletedQuiz completedQuiz = new CompletedQuiz();
            completedRepository.save(completedQuiz);
            User user = userService.getCurrentUser();
            user.getCompletedQuizzes().add(completedQuiz);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    public QuizDto getQuizById(Integer id) throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return convertToDto(quiz);
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

    public QuizDto convertToDto(Quiz quiz) {
        return modelMapper.map(quiz, QuizDto.class);
    }

    public Quiz convertToEntity(QuizDto quizDto) {
        return modelMapper.map(quizDto, Quiz.class);
    }
}
