package engine.service;

import engine.model.CompletedQuiz;
import engine.model.User;
import engine.repository.CompletedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CompletedQuizService {

    @Autowired
    private CompletedRepository repository;
    @Autowired
    private UserService userService;

    public boolean addCompletedQuiz(Integer quizId) {
        CompletedQuiz completedQuiz = repository.save(new CompletedQuiz());
        User user = userService.getCurrentUser();

        completedQuiz.setQuizId(quizId);
        user.getCompletedQuizzes().add(completedQuiz);
        return userService.updateUser(user);
    }

    public List<CompletedQuiz> getCompletedQuizzes() {
        List<CompletedQuiz> completedQuizzes = userService.getCurrentUser().getCompletedQuizzes();

        Collections.reverse(completedQuizzes);
        return completedQuizzes;
    }
}
