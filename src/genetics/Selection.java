/*
 * authors: Wojciech Kasperek & Krzysztof Spytkowski & Izabela Śmietana
 */
package genetics;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Selection {

    private static final int gameIndividualsAmount = 3; // amount of individuals that take part in every tournament
    private static final Random rand = new Random(); // object that generates random numbers

    /**
     * Invokes selection
     *
     * @param selectionType - type of selection
     * @param population - population
     */
    public static void proceedSelection(SelectionType selectionType, Population population) {
        switch (selectionType) {
            case ROULETTEWHEELSELECTION:
                rouletteWheelSelection(population);
                break;
            case TOURNAMENTSELECTION:
                tournamentSelection(population);
                break;
            case LINEARRANKINGSELECTION:
                linearRankingSelection(population);
                break;
        }
    }

    /**
     * Creates new population according to given roulette with probability of
     * choosing concrete individual
     *
     * @param population - population
     * @param roulette - list with probabilities of individuals
     */
    private static void createPopulationUsingRoulette(Population population, LinkedList<Double> roulette) {
        LinkedList<AbstractIndividual> individualsList = new LinkedList<>();
        Iterator<AbstractIndividual> individualsIterator = population.getIndividuals().iterator();
        while (individualsIterator.hasNext()) {
            int i = 0;
            double actualRouletteWheelPoint = rand.nextDouble();
            Iterator<Double> rouletteWheelIterator = roulette.iterator();
            while (rouletteWheelIterator.next() < actualRouletteWheelPoint) {
                i++;
            }
            boolean flag = false;
            for (AbstractIndividual a : individualsList) {
                if (a == population.getIndividual(i)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                switch (population.getIndividualType()) {
                    case BINARYCODEDINDIVIDUAL:
                        individualsList.add(new BinaryCodedIndividual((BinaryCodedIndividual) population.getIndividual(i)));
                        break;
                    case GROUPCODEDINDIVIDUAL:
                        individualsList.add(new GroupCodedIndividual((GroupCodedIndividual) population.getIndividual(i)));
                        break;
                }
            } else {
                individualsList.add(population.getIndividual(i));
            }
            individualsIterator.next();
        }
        population.setIndividuals(individualsList);
    }

    /**
     * Selects parents according to their fitness. The better individuals are,
     * the more chances to be selected they have.
     *
     * @param population - population
     */
    private static void rouletteWheelSelection(Population population) {
        double populationFitnessSum = population.fitnessSum();
        Iterator<AbstractIndividual> individualsIterator = population.getIndividuals().iterator();
        LinkedList<Double> rouletteWheel = new LinkedList<>();
        double lastFitness = 0;
        while (individualsIterator.hasNext()) {
            lastFitness += individualsIterator.next().getFitness() / populationFitnessSum;
            rouletteWheel.addLast(lastFitness);
        }
        rouletteWheel.removeLast();
        rouletteWheel.addLast(1.0); // to avoid some problems with number's representation in system
        createPopulationUsingRoulette(population, rouletteWheel);
    }

    /**
     * Selects parents according to their fitness. Individuals are divided into
     * small groups and from every group the best individual is selected.
     *
     * @param population - population
     * @param gameIndividualsAmount - amount of individuals taken part in every
     * tournament (only one wins)
     */
    private static void tournamentSelection(Population population) {
        LinkedList<AbstractIndividual> indiviualsList = new LinkedList<>();
        int restOfPopulation = population.getActualIndividualsAmount() % gameIndividualsAmount;
        int i = 0;
        for (; i < restOfPopulation; i++) {
            indiviualsList.add(population.getIndividual(i));
        }
        AbstractIndividual actualBestIndividual;
        for (; i < population.getActualIndividualsAmount(); i = i + gameIndividualsAmount) {
            actualBestIndividual = population.getIndividual(i);
            for (int j = i + 1; j < (i / gameIndividualsAmount + 1) * gameIndividualsAmount + restOfPopulation; j++) {
                actualBestIndividual = AbstractIndividual.isBetter(population.getIndividual(j), actualBestIndividual);
            }
            indiviualsList.add(actualBestIndividual);
        }
        population.setIndividuals(indiviualsList);
    }

    /**
     * Selects parents according to their fitness. The better individuals are,
     * the more chances to be selected they have.
     *
     * @param population - population
     */
    private static void linearRankingSelection(Population population) {
        double sumOfArithmeticSequence = ((double) (2 + population.getActualIndividualsAmount() - 1) / 2) * population.getActualIndividualsAmount();
        Collections.sort(population.getIndividuals());
        Iterator<AbstractIndividual> individualsIterator = population.getIndividuals().iterator();
        LinkedList<Double> rankingWheel = new LinkedList<>();
        double lastFitness = 0;
        double lastIndex = 0;
        while (individualsIterator.hasNext()) {
            lastFitness += ++lastIndex / sumOfArithmeticSequence;
            rankingWheel.add(lastFitness);
            individualsIterator.next();
        }
        rankingWheel.removeLast();
        rankingWheel.addLast(1.0); // to avoid some problems with number's representation in system
        createPopulationUsingRoulette(population, rankingWheel);
    }
}
