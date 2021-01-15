package engine.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Messages {
    SUCCESS(true, "Congratulations, you're right!"),
    FAIL(false, "Wrong answer! Please, try again.");
    private final boolean success;
    private final String feedback;

    Messages(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
