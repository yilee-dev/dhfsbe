package dhfsbe.store.infrastructure.exception;

public class FileCreateException extends RuntimeException {
    public FileCreateException(Throwable cause) {
        super(cause);
    }

    public FileCreateException() {
        super();
    }

    public FileCreateException(String message) {
        super(message);
    }
}
