package engine.repository;

import engine.model.CompletedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompletedRepository extends JpaRepository<CompletedQuiz, Integer> {

    List<CompletedQuiz> findAllByUserIdOrderByCompletedAtDesc(Integer userId);
}
