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
	 * Selects parents according to their fitness. The better the chromosomes are, the more chances to be selected they have.
	 * 
	 * @param population
	 *            - population
	 * @return new population
	 */
	public static Population rouletteWheelSelection(Population population) {
		double populationRatingSum = 0;
		Iterator<Individual> individualsIterator = population.getIndividuals().iterator();
		while (individualsIterator.hasNext()) {
			populationRatingSum += individualsIterator.next().getFitness();
		}
		LinkedList<Double> rouletteWheel = new LinkedList<>();
		individualsIterator = population.getIndividuals().iterator();
		double lastRating = 0;
		while (individualsIterator.hasNext()) {
			lastRating += individualsIterator.next().getFitness() / populationRatingSum;
			rouletteWheel.add(lastRating);
		}
		System.out.println(populationRatingSum); // zeby sie w Main'ie pokazalo ;)
		Population newPopulation = new Population(population.getActualIndividualsAmount(), population.getKCliqueSize());
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
		return newPopulation; // populacja rodzicow zastepuje dotychczasowa populacje
	}

	public static Population tournament(Population population, int gameIndividualsAmount) {
		// wyjatek jak populacja nie ma osobnikow -,-
		// gameIndividualsAmount musi byc > 1 => turniej wygrawa jeden osobnik
		Population newPopulation = new Population(population.getActualIndividualsAmount(), population.getKCliqueSize());
		int restOfPopulation = population.getActualIndividualsAmount() % gameIndividualsAmount;
		int i = 0;
		System.out.println(population);
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
