package genetics;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author Krzysztof Spytkowski
 * @date 29 mar 2014
 */
public class Individual implements Comparable<Individual> {

    private final byte[] vertices; // table of subgraph's vertices (0 - not exists, 1 - exists)
    private int verticesAmount; // amount of vertices in Individual
    private double rating; // shows how well individual is adopted in population

    /**
     * CopyConstructor
     *
     * @param i - individual
     */
    public Individual(Individual i) {
        this.verticesAmount = i.getVerticesAmount();
        this.vertices = i.vertices.clone();
        this.rating = i.getRating();
    }

    /**
     * Constructor //wyjasnic co to robi to nie
     * //powstaje podgraf o wielkosci podgrafRozmiar, czyli wybieramy sobie w tablicy odpowiednia ilosc wierzcholkow
     *
     * @param graphSize - graph's size (amount of vertices)
     * @param subGraphSize - k-clique size (amount of vertices)
     */
    public Individual(int graphSize, int subGraphSize) {
        // TO DO! wyjatek! subgraph nie moze byc > niz graph
        //a na cholere ci wyjątek, zrob zwykle zabezpieczenia.
        Random rand = new Random();                        
        this.verticesAmount = subGraphSize; 
        vertices = new byte[graphSize];
        // list that helps to choose random indexes (need it below)
        LinkedList<Integer> helpList = new LinkedList<>();
        for (int i = 0; i < graphSize; i++) {
            helpList.add(i);
        }
        for (int i = 0; i < subGraphSize; i++) {
            // choose index of vertex from graph that will be added to subGraph
            int k = helpList.get(rand.nextInt(graphSize - i));
            vertices[k] = 1;
            helpList.remove((Integer) k); // musi byc rzutowanie, bo chce usunac obiekt, a nie cos o indexie k
        }
    }

    /**
     * Getter
     *
     * @return rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Setter
     *
     * @param rating - rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Remove vertex (1 => 0)
     *
     * @param index - number of vertex to remove
     */
    public void removeVertex(int index) {
        vertices[index] = 0;
        verticesAmount--; //new
    }

    /**
     * Sets vertex
     *
     * @param index - index of vertex
     * @param value - value to set
     */
    public void setVertex(int index, byte value) {
    	if (vertices[index] != value && value == 1)
    		verticesAmount++;
        else if (vertices[index] != value && value == 0)
        	verticesAmount--;
        vertices[index] = value;
    }

    /**
     * Inverses (0 => 1, 1 => 0)
     *
     * @param index - index of part to inverse
     */
    public void inversePartOfGene(int index) {
        setVertex(index, (vertices[index] == 0) ? (byte) 1 : (byte) 0);
    }

    /**
     * Getter
     *
     * @param index - vertex index
     * @return value of vertex (0 - not exists, 1 - exists)
     */
    public byte getValueOfVertex(int index) {
        return vertices[index];
    }

    /**
     * Getter
     *
     * @return verticesAmount
     */
    public int getVerticesAmount() {
        return verticesAmount;
    }

    /**
     * To REMOVE!
     */
    @Override
    public String toString() {
        String s = "Osobnik: ";
        for (int i : vertices) {
            s += i;
        }
        s += " ";
        s += rating;
        return s += "\n";
    }

    /**
     * Getter
     *
     * @return table of subgraph's vertices
     */
    public byte[] getVertices() {
        return vertices;
    }

    /**
     * Na pewno poprawnie?! Potem sie sprawdzi :D
     */
    @Override
    public int compareTo(Individual i) {
        if (this.rating == i.rating) {
            return 0;
        }
        return (this.rating > i.rating) ? 1 : -1;
    }
}
