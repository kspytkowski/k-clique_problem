/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package exceptions;

public class GeneticAlgorithmException extends Exception {

    private static final long serialVersionUID = 1L; // default serial version

    /**
     * Constructor.
     */
    public GeneticAlgorithmException() {
    }

    /**
     * Constructor
     *
     * @param message - information about exception
     */
    public GeneticAlgorithmException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param cause - cause of exception
     */
    public GeneticAlgorithmException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor
     *
     * @param message - information about exception
     * @param cause - cause of exception
     */
    public GeneticAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }
}
