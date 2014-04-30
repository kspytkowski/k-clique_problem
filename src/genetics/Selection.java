package genetics;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import exceptions.GeneticAlgorithmException;

/**
 * @author Krzysztof Spytkowski
 * @date 7 kwi 2014
 */
public class Selection {

    private static final Random rand = new Random(); // object that generates random numbers

    /**
     * Creates new population according to given roulette with probability of choosing concrete individual
     * 
     * @param population
     *            - population
     * @param roulette
     *            - list with probabilities of individuals
     */
    private static void createPopulationUsingRoulette(Population population, LinkedList<Double> roulette) {
        LinkedList<AbstractIndividual> IndividualsList = new LinkedList<>();
        Iterator<AbstractIndividual> individualsIterator = population.getIndividuals().iterator();
        while (individualsIterator.hasNext()) {
            int i = 0;
            double actualRouletteWheelPoint = rand.nextDouble();
            Iterator<Double> rouletteWheelIterator = roulette.iterator();
            while (rouletteWheelIterator.next() < actualRouletteWheelPoint) {
                i++;
            }
            IndividualsList.add(population.getIndividual(i)); // populacja rodzicow, Individualse moga sie powtarzac
            individualsIterator.next();
        }
        population.setIndividuals(IndividualsList);
    }

    /**
     * Selects parents according to their fitness. The better individuals are, the more chances to be selected they have.
     * 
     * @param population
     *            - population
     */
    public static void rouletteWheelSelection(Population population) {
        double populationFitnessSum = population.fitnessSum();
        Iterator<AbstractIndividual> individualsIterator = population.getIndividuals().iterator();
        LinkedList<Double> rouletteWheel = new LinkedList<>();
        double lastFitness = 0;
        while (individualsIterator.hasNext()) {
            lastFitness += individualsIterator.next().getFitness() / populationFitnessSum;
            rouletteWheel.addLast(lastFitness);
        }
        rouletteWheel.removeLast(); // ponizej wyjasnienie
        rouletteWheel.addLast(1.0); // zeby uniknac bledow spowodowanych przez zaokraglanie itd..
        createPopulationUsingRoulette(population, rouletteWheel);
    }

    /**
     * Selects parents according to their fitness. Individuals are divided into small groups and from every group the best individual is selected.
     * 
     * @param population
     *            - population
     * @param gameIndividualsAmount
     *            - amount of individuals taken part in every tournament (only one wins)
     * @throws GeneticAlgorithmException
     */
    public static void tournamentSelection(Population population, int gameIndividualsAmount) throws GeneticAlgorithmException { // mozna dorobic, zeby bardziej losowo wybieralo osobnikow do turniejow...
        if (gameIndividualsAmount < 1 || gameIndividualsAmount > 4) {
            throw new GeneticAlgorithmException("In tournament selection amount of individuals in game should be more than 1 and less than 5");
        }
        LinkedList<AbstractIndividual> indiviualsList = new LinkedList<>();
        int restOfPopulation = population.getActualIndividualsAmount() % gameIndividualsAmount;
        int i = 0;
        for (; i < restOfPopulation; i++) { // osobniki ktore nie beda brac udzialu w turnieju, mozna w sumie ich nie brac pod uwage :) zalezy jak chcemy...
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
     * Selects parents according to their fitness. The better individuals are, the more chances to be selected they have.
     * 
     * @param population
     *            - population
     */
    // NEED TO BE TESTED! wydaje sie teraz dzialac poprawnie
    public static void linearRankingSelection(Population population) {
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
        rankingWheel.removeLast(); // ponizej wyjasnienie
        rankingWheel.addLast(1.0); // zeby uniknac bledow spowodowanych przez zaokraglanie itd..
        createPopulationUsingRoulette(population, rankingWheel);
    }
}
