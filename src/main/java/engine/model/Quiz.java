package engine.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String text;
    @ElementCollection
    @OrderColumn(name = "ID")
    private List<String> options;
    @ElementCollection
    @OrderColumn(name = "ID")
    private List<Integer> answers;

    @ManyToOne
    @JoinTable(
            name = "QUIZ_USER",
            joinColumns = @JoinColumn(name = "QUIZ_FK"),
            inverseJoinColumns = @JoinColumn(name = "USER_FK"))
    private User owner;
}
