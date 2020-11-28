package engine.dto;

import engine.model.Quiz;
import engine.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class ConvertDto {

    public static QuizDto quizToDto(Quiz quiz) {
        QuizDto quizDto = new QuizDto();

        quizDto.setId(quiz.getId());
        quizDto.setTitle(quiz.getTitle());
        quizDto.setText(quiz.getText());
        quizDto.setOptions(quiz.getOptions());

        return quizDto;
    }

    public static Quiz dtoToQuiz(QuizDto quizDto, User user) {
        Quiz quiz = new Quiz();

        quiz.setId(quizDto.getId());
        quiz.setTitle(quizDto.getTitle());
        quiz.setText(quizDto.getText());
        quiz.setOptions(quizDto.getOptions());
        quiz.setAnswer(quizDto.getAnswer());
        quiz.setUser(user);

        return quiz;
    }
}
