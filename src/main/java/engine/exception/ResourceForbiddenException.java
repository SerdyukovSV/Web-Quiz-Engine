package engine.exception;

public class ResourceForbiddenException extends RuntimeException {

    public ResourceForbiddenException() {
    }

    public ResourceForbiddenException(String message) {
        super(message);
    }
}
