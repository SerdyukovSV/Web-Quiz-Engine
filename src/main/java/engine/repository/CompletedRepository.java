package engine.repository;

//import engine.model.Completed;
import engine.model.CompletedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedRepository extends JpaRepository<CompletedQuiz, Long> {

    public Page<CompletedQuiz> findByUserId(Long id, Pageable pageable);
}
