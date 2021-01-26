package engine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class QuizDto {

    private Integer id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Text is mandatory")
    private String text;
    @NotNull(message = "Options is mandatory")
    @Size(min = 2, message = "Must be greater than or equal to 2")
    private List<String> options;
    private List<Integer> answers;
    @JsonIgnore
    private UserDto owner;

    @JsonIgnore
    public List<Integer> getAnswers() {
        return answers;
    }

    @JsonSetter
    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }
}
