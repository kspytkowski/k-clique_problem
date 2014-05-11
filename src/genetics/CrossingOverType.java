package genetics;

/**
 * @author Krzysztof Spytkowski
 * @date 6th April 2014
 */
public enum CrossingOverType {

    ONEPOINTWITHTWOCHILDREN, ONEPOINTWITHONECHILD, UNIFORMCROSSOVER, WEIGHTEDUNIFORMCROSSOVER, TWOPOINTSWITHTWOCHILDREN, TWOPOINTSWITHONECHILD;

    /**
     * Gets type at given index
     *
     * @param index
     * @return crossing over type
     */
    public static CrossingOverType getAtIndex(int index) {
        switch (index) {
            case 0:
                return ONEPOINTWITHTWOCHILDREN;
            case 1:
                return ONEPOINTWITHONECHILD;
            case 2:
                return UNIFORMCROSSOVER;
            case 3:
                return WEIGHTEDUNIFORMCROSSOVER;
            case 4:
                return TWOPOINTSWITHTWOCHILDREN;
            case 5:
                return TWOPOINTSWITHONECHILD;
            default:
                return ONEPOINTWITHTWOCHILDREN;
        }
    }
}
