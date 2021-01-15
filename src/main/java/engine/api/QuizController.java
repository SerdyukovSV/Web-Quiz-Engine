package engine.api;

import engine.dto.QuizDto;
import engine.exception.ResourceNotFoundException;
import engine.model.Answer;
import engine.model.CompletedQuiz;
import engine.model.Messages;
import engine.model.Quiz;
import engine.service.QuizService;
import engine.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "api/quizzes")
public class QuizController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuizService quizService;

    @PostMapping
    public ResponseEntity<QuizDto> createQuiz(@Valid @RequestBody QuizDto quizDto) {
        return new ResponseEntity<>(
                quizService.createQuiz(quizDto),
                HttpStatus.CREATED
        );
    }

    @PostMapping(path = "/{id}/solve")
    public Messages solveQuiz(@PathVariable Integer id,
                              @RequestBody Answer answer) throws ResourceNotFoundException {
        if (quizService.solveQuiz(id, answer)) {
            return Messages.SUCCESS;
        }
        return Messages.FAIL;
    }

    @GetMapping(path = "/completed")
    public List<CompletedQuiz> getCompletedQuizzes() {
        return userService.completedQuizzes();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<QuizDto> getQuiz(
            @PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(quizService.getQuizById(id));
    }

    @GetMapping
    public List<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page) {
        return quizService.getAllQuizzes(page);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteQuiz(
            @PathVariable Integer id) throws ResourceNotFoundException {
        if (quizService.deleteQuiz(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
