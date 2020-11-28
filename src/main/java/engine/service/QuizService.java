package engine.service;

import engine.model.Quiz;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    public Page getAllQuizzes(Integer pageNo) {
        Integer pageSize = 10;

        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<Quiz> pagedResult = quizRepository.findAll(paging);

        return pagedResult;
    }
}
