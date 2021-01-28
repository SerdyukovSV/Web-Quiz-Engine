package engine.service;

import engine.dto.AnswerDto;
import engine.dto.QuizDto;
import engine.dto.UserDto;
import engine.exception.QuizAlreadyExistException;
import engine.exception.ResourceNotFoundException;
import engine.model.Quiz;
import engine.model.User;
import engine.repository.QuizRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = QuizService.class)
class QuizServiceTest {

    Logger logger = Logger.getLogger(QuizServiceTest.class.getName());

    @Autowired
    private QuizService quizService;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private QuizRepository quizRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private CompletedQuizService completedQuizService;

    @Test
    void create_Should_Return_SavedQuiz() {
        QuizDto quizDto = new QuizDto();
        quizDto.setTitle("qwe");
        Quiz entity = new Quiz();
        UserDto userDto = new UserDto();

        when(quizRepository.findByTitle(anyString())).thenReturn(null);
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(modelMapper.map(quizDto, Quiz.class)).thenReturn(entity);
        Quiz saved = new Quiz();
        saved.setId(1);
        when(quizRepository.save(entity)).thenReturn(saved);
        QuizDto quizRet = new QuizDto();
        when(modelMapper.map(saved, QuizDto.class)).thenReturn(quizRet);

        QuizDto actual = quizService.create(quizDto);

        assertEquals(quizRet, actual);

    }

    @Test
    void create_Should_Throw_Exception() {
        QuizDto quizDto = new QuizDto();
        quizDto.setTitle("qwe");

        when(quizRepository.findByTitle(anyString())).thenReturn(new Quiz());

        Exception exception = assertThrows(QuizAlreadyExistException.class,
                () -> quizService.create(quizDto));
        String actual = exception.getMessage();
        String expected = "Quiz qwe already exists";

        assertEquals(expected, actual);
    }

    @Test
    void solve_Should_Return_True() {
        Integer quizId = 1;
        AnswerDto answerDto = new AnswerDto();
        answerDto.setAnswers(Arrays.asList(0, 2));
        Quiz quiz = new Quiz();
        quiz.setAnswers(Arrays.asList(2, 0));

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        boolean actual = quizService.solve(quizId, answerDto);

        assertTrue(actual);
    }

    @Test
    void solve_Should_Return_False() {
        Integer quizId = 1;
        AnswerDto answerDto = new AnswerDto();
        answerDto.setAnswers(Arrays.asList(0, 2));
        Quiz quiz = new Quiz();
        quiz.setAnswers(Arrays.asList(1, 2));

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        boolean actual = quizService.solve(quizId, answerDto);

        assertFalse(actual);
    }

    @Test
    void solve_Should_Throw_Exception() {
        AnswerDto answerDto = new AnswerDto();

        when(quizRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> quizService.solve(1, answerDto));
    }

    @Test
    void getById_Should_Return_Quiz() {
        Integer quizId = 1;
        Quiz quiz = new Quiz();
        QuizDto quizDto = new QuizDto();

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(modelMapper.map(quiz, QuizDto.class)).thenReturn(quizDto);
        QuizDto quizRet = quizService.getById(quizId);

        assertEquals(quizDto, quizRet);
    }

    @Test
    void getById_Should_Throw_Exception() {

        when(quizRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> quizService.getById(1));
    }

    @Test
    void getAll_Should_Return_ListQuiz() {
        Quiz quiz1 = new Quiz();
        Quiz quiz2 = new Quiz();
        Page<Quiz> quizzes = new PageImpl<>(
                Arrays.asList(quiz1, quiz2)
        );

        when(quizRepository.findAll(isA(Pageable.class))).thenReturn(quizzes);

        QuizDto quizDto1 = new QuizDto();
        QuizDto quizDto2 = new QuizDto();
        List<QuizDto> retList = Arrays.asList(quizDto1, quizDto2);

        when(modelMapper.map(quiz1, QuizDto.class)).thenReturn(quizDto1);
        when(modelMapper.map(quiz2, QuizDto.class)).thenReturn(quizDto2);

        List<QuizDto> actualList = quizService.getAll(1);

        assertEquals(retList.size(), actualList.size());
    }

    @Test
    void delete_Should_Return_True() {
        Quiz entity = new Quiz();
        User user = new User();
        UserDto userDto = new UserDto();

        entity.setOwner(user);
        user.setId(1);
        userDto.setId(1);

        when(quizRepository.findById(1)).thenReturn(Optional.of(entity));
        when(userService.getCurrentUser()).thenReturn(userDto);

        boolean actual = quizService.delete(1);

        assertTrue(actual);
    }

    @Test
    void delete_Should_Return_False() {
        Quiz entity = new Quiz();
        User user = new User();
        UserDto userDto = new UserDto();

        entity.setOwner(user);
        user.setId(1);
        userDto.setId(2);

        when(quizRepository.findById(1)).thenReturn(Optional.of(entity));
        when(userService.getCurrentUser()).thenReturn(userDto);

        boolean actual = quizService.delete(1);

        assertFalse(actual);
    }

    @Test
    void delete_Should_Throw_Exception() {

        when(quizRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> quizService.delete(1));
    }
}