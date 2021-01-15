package engine.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class CompletedQuiz implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private final String completedAt = LocalDateTime.now().toString();
}
