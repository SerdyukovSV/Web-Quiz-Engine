package engine.model;

import lombok.Data;
import org.hibernate.annotations.SortComparator;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.SortedSet;
import java.util.TreeSet;

@Data
@Entity
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
    @SortNatural
    private SortedSet<Integer> answers;

    @ManyToOne
    @JoinTable(
            name = "QUIZ_USER",
            joinColumns = @JoinColumn(name = "QUIZ_FK"),
            inverseJoinColumns = @JoinColumn(name = "USER_FK")
    )
    private User owner;
}
