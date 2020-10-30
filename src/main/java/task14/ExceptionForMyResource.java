package task14;

public class ExceptionForMyResource extends Exception{

    public ExceptionForMyResource() {}

    public ExceptionForMyResource(String message) {
        super(message);
    }

    public ExceptionForMyResource(String message, Throwable cause) {
        super(message) ;
        initCause(cause);
    }
}
