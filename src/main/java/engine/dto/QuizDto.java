package engine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import engine.model.Answer;
import lombok.Data;
import org.springframework.objenesis.instantiator.android.AndroidSerializationInstantiator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private Integer[] answer;

    @JsonIgnore
    public Integer[] getAnswer() {
        return answer;
    }

    @JsonSetter
    public void setAnswer(Integer[] answer) {
        this.answer = answer;
    }
}
