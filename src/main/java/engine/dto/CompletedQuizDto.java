package engine.dto;

import lombok.Data;

@Data
public class CompletedQuizDto {

    private Integer quizId;
    private String completedAt;
}
