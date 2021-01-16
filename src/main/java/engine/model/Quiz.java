package engine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    private String title;
    private String text;
    @ElementCollection
    @OrderColumn(name = "ID")
    private String[] options;
    @ElementCollection
    @OrderColumn(name = "ID")
    private Integer[] answer;

    @ManyToOne
    @JoinTable(
            name = "QUIZ_USER",
            joinColumns = @JoinColumn(name = "QUIZ_FK"),
            inverseJoinColumns = @JoinColumn(name = "USER_FK")
    )
    private User owner;
}
