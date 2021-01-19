package engine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.SortedSet;

@Data
public class QuizDto {

    private Integer id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Text is mandatory")
    private String text;
    @NotNull(message = "Options is mandatory")
    @Size(min = 2, message = "Must be greater than or equal to 2")
    private String[] options;
    private SortedSet<Integer> answers;

    @JsonIgnore
    public SortedSet<Integer> getAnswers() {
        return answers;
    }

    @JsonSetter
    public void setAnswers(SortedSet<Integer> answers) {
        this.answers = answers;
    }
}
