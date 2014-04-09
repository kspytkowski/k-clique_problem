package genetics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 7 kwi 2014
 */
public class Selection {

	private static Random rand = new Random(); // object that generates random numbers

	/**
	 * Selects parents according to their fitness. The better individuals are, the more chances to be selected they have.
	 * 
	 * @param population
	 *            - population
	 * @return new population (parents)
	 */
	public static Population rouletteWheelSelection(Population population) {
		double populationFitnessSum = population.fitnessSum();
		Iterator<Individual> individualsIterator = population.getIndividuals().iterator();
		LinkedList<Double> rouletteWheel = new LinkedList<>();
		individualsIterator = population.getIndividuals().iterator();
		double lastFitness = 0;
		while (individualsIterator.hasNext()) {
			lastFitness += individualsIterator.next().getFitness() / populationFitnessSum;
			rouletteWheel.add(lastFitness);
		}
		Population newPopulation = new Population(population.getDemandedIndividualsAmount(), population.getGraphRepresentation(), population.getKCliqueSize());
		individualsIterator = population.getIndividuals().iterator();
		while (individualsIterator.hasNext()) {
			int i = 0;
			double actualRouletteWheelPoint = rand.nextDouble();
			Iterator<Double> rouletteWheelIterator = rouletteWheel.iterator();
			while (rouletteWheelIterator.next() < actualRouletteWheelPoint) {
				i++;
			}
			newPopulation.addIndividual(population.getIndividual(i)); // populacja rodzicow, Individualse moga sie powtarzac
			individualsIterator.next();
		}
		return newPopulation;
	}

	/**
	 * Selects parents according to their fitness. Individuals are divided into small groups and from every group the best individual is selected.
	 * 
	 * @param population
	 *            - population
	 * @param gameIndividualsAmount
	 *            - amount of individuals taken part in every tournament (only one wins)
	 * @return new population (parents)
	 */
	public static Population tournament(Population population, int gameIndividualsAmount) { // mozna dorobic, zeby bardziej losowo wybieralo osobnikow do turniejow...
		// gameIndividualsAmount musi byc > 1 => turniej wygrawa jeden osobnik
		Population newPopulation = new Population(population.getDemandedIndividualsAmount(), population.getGraphRepresentation(), population.getKCliqueSize());
		int restOfPopulation = population.getActualIndividualsAmount() % gameIndividualsAmount;
		int i = 0;
		for (; i < restOfPopulation; i++) { // osobniki ktore nie beda brac udzialu w turnieju
			newPopulation.addIndividual(population.getIndividual(i));
		}
		Individual actualBestIndividual;
		for (; i < population.getActualIndividualsAmount(); i = i + gameIndividualsAmount) {
			actualBestIndividual = population.getIndividual(i);
			for (int j = i + 1; j < (i / gameIndividualsAmount + 1) * gameIndividualsAmount + restOfPopulation; j++) {
				actualBestIndividual = Individual.isBetter(population.getIndividual(j), actualBestIndividual);
			}
			newPopulation.addIndividual(actualBestIndividual);
		}
		return newPopulation;
	}
}
