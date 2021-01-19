package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class CompletedQuiz implements Serializable {

    @Id
    @JsonIgnore
    @GeneratedValue
    private Integer id;
    private Integer quizId;
    private final String completedAt = LocalDateTime.now().toString();
}
