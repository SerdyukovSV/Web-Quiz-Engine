package engine.exception;

public class QuizAlreadyExistException extends RuntimeException {

    public QuizAlreadyExistException() {
        super();
    }

    public QuizAlreadyExistException(String message) {
        super(message);
    }
}
