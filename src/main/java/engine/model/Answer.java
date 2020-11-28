package engine.model;

import org.springframework.stereotype.Component;


public class Answer {

    private Long[] answer;

    public Answer(){
    }

    public Answer(Long[] answer) {
        this.answer = answer;
    }

    public Long[] getAnswer() {
        return answer;
    }

    public void setAnswer(Long[] answer) {
        this.answer = answer;
    }
}
