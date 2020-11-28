package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CompletedQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "completed_id")
    private Long completedId;

    private Long id;

    private Date completedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CompletedQuiz() {
    }

    public CompletedQuiz(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public void setCompletedId(Long completedId) {
        this.completedId = completedId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    @JsonSetter
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CompletedQuiz{" +
                "completedId=" + id +
                ", id=" + id +
                ", completedAt=" + completedAt +
                ", userId=" + user.getId() +
                '}';
    }
}
