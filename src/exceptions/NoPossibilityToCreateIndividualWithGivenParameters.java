/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski
 * Each line should be prefixed with  * 
 */

package exceptions;

/**
 *
 * @author wukat
 */
public class NoPossibilityToCreateIndividualWhichYoudLikeToCreateAndICannotChangeItSoIThrowAnExceptionAndIThinkYouWillLikeItBecauseIDontHaveAnythingBetterToDoItsOnly2OClockNightIsYoung extends Exception {
    
    private static final long serialVersionUID = 1L; // default serial version

    /**
     * Constructor
     */
    public NoPossibilityToCreateIndividualWhichYoudLikeToCreateAndICannotChangeItSoIThrowAnExceptionAndIThinkYouWillLikeItBecauseIDontHaveAnythingBetterToDoItsOnly2OClockNightIsYoung() {
    }

    /**
     * Constructor
     * 
     * @param message
     *            - information about exception
     */
    public NoPossibilityToCreateIndividualWhichYoudLikeToCreateAndICannotChangeItSoIThrowAnExceptionAndIThinkYouWillLikeItBecauseIDontHaveAnythingBetterToDoItsOnly2OClockNightIsYoung(String message) {
        super(message);
    }

    /**
     * Constructor
     * 
     * @param cause
     *            - cause of exception
     */
    public NoPossibilityToCreateIndividualWhichYoudLikeToCreateAndICannotChangeItSoIThrowAnExceptionAndIThinkYouWillLikeItBecauseIDontHaveAnythingBetterToDoItsOnly2OClockNightIsYoung(Throwable cause) {
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
    public NoPossibilityToCreateIndividualWhichYoudLikeToCreateAndICannotChangeItSoIThrowAnExceptionAndIThinkYouWillLikeItBecauseIDontHaveAnythingBetterToDoItsOnly2OClockNightIsYoung(String message, Throwable cause) {
        super(message, cause);
    }
}
