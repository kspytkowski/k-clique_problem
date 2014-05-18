/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package exceptions;

public class NoPossibilityToCreateGraphException extends Exception {

    private static final long serialVersionUID = 1L; // default serial version

    /**
     * Constructor.
     */
    public NoPossibilityToCreateGraphException() {
    }

    /**
     * Constructor
     *
     * @param message - information about exception
     */
    public NoPossibilityToCreateGraphException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param cause - cause of exception
     */
    public NoPossibilityToCreateGraphException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor
     *
     * @param message - information about exception
     * @param cause - cause of exception
     */
    public NoPossibilityToCreateGraphException(String message, Throwable cause) {
        super(message, cause);
    }
}
