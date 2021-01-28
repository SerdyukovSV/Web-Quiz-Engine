package engine.api;

import engine.dto.AnswerDto;
import engine.dto.CompletedQuizDto;
import engine.dto.QuizDto;
import engine.model.Messages;
import engine.service.CompletedQuizService;
import engine.service.QuizService;
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

    private final CompletedQuizService completedService;
    private final QuizService quizService;

    @Autowired
    public QuizController(CompletedQuizService completedService, QuizService quizService) {
        this.completedService = completedService;
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizDto> createQuiz(@Valid @RequestBody QuizDto quizDto) {
        log.debug("Method POST 'createQuiz' started with arg {}", quizDto);
        return new ResponseEntity<>(quizService.create(quizDto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/{id}/solve")
    public ResponseEntity<Messages> solveQuiz(@PathVariable Integer id, @RequestBody AnswerDto answerDto) {
        log.debug("Method POST 'solveQuiz' started with arg {}, {}", id, answerDto);
        if (quizService.solve(id, answerDto)) {
            return new ResponseEntity<>(Messages.SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(Messages.FAIL, HttpStatus.OK);
    }

    @GetMapping(path = "completed")
    public List<CompletedQuizDto> getCompletedQuizzes() {
        log.debug("Method GET 'getCompletedQuizzes' started");
        return completedService.getAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<QuizDto> getQuiz(@PathVariable Integer id) {
        log.debug("Method GET 'getQuiz' started with arg {}", id);
        return new ResponseEntity<>(quizService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public List<QuizDto> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page) {
        log.debug("Method GET 'getAllQuizzes' started with arg {}", page);
        return quizService.getAll(page);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteQuiz(@PathVariable Integer id) {
        log.debug("Method DELETE 'deleteQuiz' started with arg {}", id);
        if (quizService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
