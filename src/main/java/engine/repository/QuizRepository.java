package engine.repository;

import engine.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    Quiz findByTitle(String title);

//    Quiz findByIdAndByOwnerId(Integer id, Integer ownerId);
}
