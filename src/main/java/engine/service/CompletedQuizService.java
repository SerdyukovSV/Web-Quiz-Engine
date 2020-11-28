package engine.service;

import engine.model.CompletedQuiz;
import engine.repository.CompletedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletedQuizService {

    @Autowired
    CompletedRepository completedRepository;

    @Autowired
    AuthenticationUsers authUser;

    public Page getAllCompletedQuiz(Integer pageNo) {
        Integer pageSize = 10;

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("completedAt").descending());

        Page<CompletedQuiz> resultPage = completedRepository
                .findByUserId(authUser.getAuthenticationUsers().getId(), paging);

        return resultPage;
    }
}
