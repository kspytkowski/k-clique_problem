/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski
 * Each line should be prefixed with  * 
 */

package exceptions;

/**
 *
 * @author wukat
 */
public class NoPossibilityToCreateIndividualWithGivenParameters extends Exception {
    
    private static final long serialVersionUID = 1L; // default serial version

    /**
     * Constructor
     */
    public NoPossibilityToCreateIndividualWithGivenParameters() {
    }

    /**
     * Constructor
     * 
     * @param message
     *            - information about exception
     */
    public NoPossibilityToCreateIndividualWithGivenParameters(String message) {
        super(message);
    }

    /**
     * Constructor
     * 
     * @param cause
     *            - cause of exception
     */
    public NoPossibilityToCreateIndividualWithGivenParameters(Throwable cause) {
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
    public NoPossibilityToCreateIndividualWithGivenParameters(String message, Throwable cause) {
        super(message, cause);
    }
}
