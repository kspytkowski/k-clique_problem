/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package genetics;

public enum SelectionType {

    TOURNAMENTSELECTION, ROULETTEWHEELSELECTION, LINEARRANKINGSELECTION;

    /**
     * Gets type at given index.
     *
     * @param index
     * @return type of selection
     */
    public static SelectionType getAtIndex(int index) {
        switch (index) {
            case 0:
                return TOURNAMENTSELECTION;
            case 1:
                return ROULETTEWHEELSELECTION;
            case 2:
                return LINEARRANKINGSELECTION;
            default:
                return ROULETTEWHEELSELECTION;
        }
    }
}
