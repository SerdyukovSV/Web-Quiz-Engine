package engine.api;

import engine.dto.ConvertDto;
import engine.dto.QuizDto;
import engine.model.*;
import engine.exception.ResourceNotFoundException;
import engine.repository.CompletedRepository;
import engine.repository.QuizRepository;
import engine.repository.UsersRepository;
import engine.service.AuthenticationUsers;
import engine.service.CompletedQuizService;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.*;

@RestController
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CompletedRepository completedRepository;

    @Autowired
    QuizService quizService;

    @Autowired
    AuthenticationUsers authUser;

    @Autowired
    CompletedQuizService completedQuizService;

    Message success = new Message(true, "Congratulations, you're right!");
    Message fail = new Message(false, "Wrong answer! Please, try again.");

    public QuizController() {
    }

    @PostMapping(path = "/api/quizzes")
    public QuizDto createQuiz(@Valid @RequestBody QuizDto quizDto) {
        Quiz quiz = ConvertDto.dtoToQuiz(quizDto, authUser.getAuthenticationUsers());
        quizRepository.save(quiz);
        quizDto.setId(quiz.getId());
        return quizDto;
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public Message solveQuiz(@PathVariable long id, @RequestBody Answer answer) throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        if (isCorrectAnswer(quiz, answer)) {
            CompletedQuiz completedQuiz = new CompletedQuiz(quiz.getId(), authUser.getAuthenticationUsers());
            completedRepository.save(completedQuiz);
            return success;
        }
        return fail;
    }

    @GetMapping(path = "/api/quizzes/completed")
    public Page getAllQuizCompleted(@RequestParam(defaultValue = "0") Integer page) {
        return completedQuizService.getAllCompletedQuiz(page);
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Long id) throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        return ResponseEntity.ok().body(ConvertDto.quizToDto(quiz));
    }

    @GetMapping(path = "/api/quizzes")
    public Page getAllQuizzes(@RequestParam(defaultValue = "0") Integer page) {
        return quizService.getAllQuizzes(page);
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable Long id) throws ResourceNotFoundException {

        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());

        if (quiz.getUser().getId() == authUser.getAuthenticationUsers().getId()) {
            quizRepository.delete(quiz);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private boolean isCorrectAnswer(Quiz quiz, Answer answer) {
        if (Arrays.equals(quiz.getAnswer(), answer.getAnswer())) {
            return true;
        } else if (quiz.getAnswer() == null && answer.getAnswer().length == 0) {
            return true;
        } else if (answer.getAnswer() == null && quiz.getAnswer().length == 0) {
            return true;
        }
        return false;
    }

}
