package genetics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Population {

    private final LinkedList<Individual> individuals; // list of individuals
    private int individualsAmount; // amount of individuals in population
    private final double interbreedingProbability; // individuals' interbreeding probability
    private final double mutationProbability; // individuals' mutation probability
    private final Random rand = new Random();

    /**
     * Constructor
     *
     * @param individualsAmount - initially amount of individuals
     * @param graphSize - graph's size (amount of vertices)
     * @param subGraphSize - k-clique size (amount of vertices)
     * @param interbreedingProbability - individuals' interbreeding probability
     * @param mutationProbability - individuals' mutation probability
     */
    public Population(int individualsAmount, int graphSize, int subGraphSize, double interbreedingProbability, double mutationProbability) {
        this.individualsAmount = individualsAmount;
        this.interbreedingProbability = interbreedingProbability;
        this.mutationProbability = mutationProbability;
        individuals = new LinkedList<>();
        for (int i = 0; i < individualsAmount; i++) {
            individuals.add(new Individual(graphSize, subGraphSize));
        }
    }

	public void rouletteSelection() {
		double populationRatingSum = 0;
		Iterator<Individual> individualsIterator = individuals.iterator();
		// przystosowanie przynajmniej jednego osobnika musi byc > 0! -> zadbaj o to :D
		// liczymy sume przystosowan wszystkich osobnikow
		while(individualsIterator.hasNext()) {
			populationRatingSum += individualsIterator.next().getRating();
		}
		// robimy tzm. "kolo ruletki", iteracyjnie posumowane prawdopodobienstwa
		// np. dla populacji 6 osobnikow:
		// przystosowania poszczeg. osobnikow => 1,2,5,1,6,5
		// suma przystosowan = 20
		// rouletteWheel (nasze kolo) => 1/20, 1/20 + 2/20, 1/20 + 2/20 + 5/20 itd.
		LinkedList<Double> rouletteWheel = new LinkedList<>();
		individualsIterator = individuals.iterator();
		double lastRating = 0;
		while(individualsIterator.hasNext()) {
			lastRating += individualsIterator.next().getRating() / populationRatingSum;
			rouletteWheel.add(lastRating);
		}
		System.out.println(populationRatingSum); // zeby sie w Main'ie pokazalo ;)
		// teraz tworzymy nowe pokolenie, wybierajac rodzicow z listy individualsow na podstawie ich selectionProbability (powyzej stworzonego kola)
		LinkedList<Individual> individualsParents = new LinkedList<>();
		individualsIterator = individuals.iterator();
		while(individualsIterator.hasNext()) {
			int i = 0;
			// przy ruletce co iteracje wybieramy losowy punkt na kole ruletki
			double actualRouletteWheelPoint = rand.nextDouble();
			Iterator<Double> rouletteWheelIterator = rouletteWheel.iterator();
			// szukamy odpowiedniego osobnika na naszym kole ruletki
			while (rouletteWheelIterator.next() < actualRouletteWheelPoint) {
				i++;
			}
			// dodajemy "wylosowanego" osobnika do nowej poluacji
			individualsParents.add(individuals.get(i)); // populacja rodzicow, Individualse moga sie powtarzac
			individualsIterator.next();
		}
		individuals = individualsParents; // populacja rodzicow zastepuje dotychczasowa populacje
	}
	
	// tylko do pomocy, trzeba cos konkretnego napisac...
	public void dostosowanie() {
		//tu ustawimy rating osobnikom, tylko dla sprawdzenia poprawnosci ruletki...
		Iterator<Individual> it = individuals.iterator();
		while(it.hasNext()) {
			Individual ind = it.next();
			int lol = 0;
			for (int i =0; i < ind.getVerticesAmount(); i++) {
				if (ind.getVertices()[i] == 1)
					lol += 1;
			}
			ind.setRating(lol);
		}
		
	}
	/**
	 * Starts appropriate interbreeding
	 * 
	 * @param which
	 *            - type of interbreeding
	 */
	public void initializeCrossingOver(int which) { // TO DO zrob z which ENUMa
		Random rand = new Random();
		int amountOfIndividualsToInterbreed = (int) (interbreedingProbability * individualsAmount);
		if (amountOfIndividualsToInterbreed % 2 == 1)
			amountOfIndividualsToInterbreed--; // liczba rodzicow musi byc parzysta :D
		// Pomocne przy losowaniu RANDomowych rodzicow
		LinkedList<Integer> numbers = new LinkedList<Integer>();
		for (int i = 0; i < amountOfIndividualsToInterbreed; i++) {
			numbers.add(new Integer(i));
		}
		// W kazdym obiegu petli wez 2 sposrod losowo stworzonej (przez selekcje) paczki rodzicow i ich krzyzuj (rodzicow bierzemy z poczatku listy)
		for (int i = 0; i < amountOfIndividualsToInterbreed / 2; i++) {
			int indexOfFirstParent = numbers.get(rand.nextInt(numbers.size()));
			numbers.remove((Integer) indexOfFirstParent);
			int indexOfSecondParent = numbers.get(rand.nextInt(numbers.size()));
			numbers.remove((Integer) indexOfSecondParent); // jak dasz Integer to wiadomo ze chodzi o obiekt a nie index, dla indexu sa bledy :P
			Individual firstParent = individuals.get(indexOfFirstParent);
			Individual secondParent = individuals.get(indexOfSecondParent);
			crossOver(firstParent, secondParent); // tu bedzie switch - wybor odpowiedniego krzyzowania
			individuals.remove(firstParent); // usuwamy rodzicow, ktorzy wyprodukowali dzieci
			individuals.remove(secondParent);
		}

	}

    // TO DO Krzysztof - zmien nazwy tych funkcji
    // [0,0,0,0,0,0,0] => [0,0,1,1,1,1]
    // [1,1,1,1,1,1,1] => [1,1,0,0,0,0] (two parents => two children)
    /**
     * Interbreeds two Individuals - parents and makes two new Individuals -
     * children
     *
     * @param firstParent - first parent
     * @param secondParent - second parent
     */
    public void interbreeding(Individual firstParent, Individual secondParent) {
        Individual firstChild = new Individual(firstParent.getSize());
        Individual secondChild = new Individual(secondParent.getSize());
        int splitPoint = rand.nextInt(firstParent.getSize()); // punkt przeciecia genomu albo chromosomu (nomenklatura...)
        for (int j = 0; j < splitPoint; j++) {
            firstChild.setVertex(j, firstParent.getValueOfVertex(j));
            secondChild.setVertex(j, secondParent.getValueOfVertex(j));
        }
        for (int j = splitPoint; j < firstParent.getSize(); j++) {
            firstChild.setVertex(j, secondParent.getValueOfVertex(j));
            secondChild.setVertex(j, firstParent.getValueOfVertex(j));
        }
        individuals.addLast(firstChild); // dodajemy NA KONCU listy dzieci (zawsze)
        individuals.addLast(secondChild);
    }

	// [0,0,0,0,0,0,0] => [0,0,0,1,1,1]
    // [1,1,1,1,1,1,1] => (two parents => one child)
    /**
     * Interbreeds two Individuals - parents and makes one new Individual -
     * child
     *
     * @param firstParent - first parent
     * @param secondParent - second parent
     */
    public void crossOver2(Individual firstParent, Individual secondParent) {
        Individual child = new Individual(firstParent.getSize());
        int splitPoint = rand.nextInt(firstParent.getSize()); // czy tu nie rand.nextInt(firstParent.getSize() - 1) + 1 ?
        for (int j = 0; j < splitPoint; j++) {
            child.setVertex(j, firstParent.getValueOfVertex(j));
        }
        for (int j = splitPoint; j < firstParent.getSize(); j++) {
            child.setVertex(j, secondParent.getValueOfVertex(j));
        }
        individuals.addLast(child);
        individualsAmount--;
    }

    // [0,0,0,0,0,0,0] => [0,1,1,1,0,1]
    // [1,1,1,1,1,1,1] => (two parents => one child)
    /**
     * Interbreeds two Individuals - parents and makes one new Individual -
     * child
     *
     * @param firstParent - first parent
     * @param secondParent - second parent
     */
    public void crossOver3(Individual firstParent, Individual secondParent) {
        Individual child = new Individual(firstParent.getSize());
        for (int j = 0; j < firstParent.getSize(); j++) {
            if (rand.nextBoolean()) {
                child.setVertex(j, firstParent.getValueOfVertex(j));
            } else {
                child.setVertex(j, secondParent.getValueOfVertex(j));
            }
        }
        individuals.addLast(child);
        individualsAmount--;
    }

    /**
     * Makes small mutations among individuals in population
     */
    public void mutate() {
        int amountOfIndividualsToMutate = (int) (mutationProbability * individualsAmount); // liczba osobnikow do mutacji
        for (int i = 0; i < amountOfIndividualsToMutate; i++) { // losuj osobniki i mutuj - zmien jeden gen chromosomu (0 => 1 lub 1 => 0)
            Individual ind = individuals.get(rand.nextInt(individualsAmount));
            int positionInGeneToChange = rand.nextInt(ind.getSize());
            ind.inversePartOfGene(positionInGeneToChange);
        }
    }

    /**
     * Getter
     *
     * @param index - index of individual
     * @return individual
     */
    public Individual getIndividual(int index) {
        return individuals.get(index);
    }

    /**
     * Adds individual to population
     *
     * @param i - individual
     */
    public void addIndividual(Individual i) {
        individuals.add(i);
    }

    /**
     * Removes individual from population
     *
     * @param index - index of individual
     */
    public void removeIndividual(int index) {
        individuals.remove(index);
    }

    @Override
    public String toString() {
        String s = "Populacja: \n";
        for (Individual ind : individuals) {
            s += ind;
        }
        return s;
    }
}
