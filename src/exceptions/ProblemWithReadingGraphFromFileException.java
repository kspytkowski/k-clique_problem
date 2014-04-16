package exceptions;

/**
 * @author Krzysztof Spytkowski
 * @date 16 kwi 2014
 */
public class ProblemWithReadingGraphFromFileException extends Exception {

    private static final long serialVersionUID = 1L; // default serial version

    /**
     * Constructor
     */
    public ProblemWithReadingGraphFromFileException() {
    }

    /**
     * Constructor
     * 
     * @param message
     *            - information about exception
     */
    public ProblemWithReadingGraphFromFileException(String message) {
        super(message);
    }

    /**
     * Constructor
     * 
     * @param cause
     *            - cause of exception
     */
    public ProblemWithReadingGraphFromFileException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor
     * 
     * @param message
     *            - information about exception
     * @param cause
     *            - cause of exception
     */
    public ProblemWithReadingGraphFromFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
