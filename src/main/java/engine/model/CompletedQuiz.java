package engine.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class CompletedQuiz {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer quizId;
    private Integer userId;
    private String completedAt = LocalDateTime.now().toString();
}
