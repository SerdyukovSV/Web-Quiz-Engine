package engine.repository;

import engine.model.CompletedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedRepository extends JpaRepository<CompletedQuiz, Integer> {
}
