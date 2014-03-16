package graph;

/**
 * @author Krzysztof Spytkowski
 * @date 16 mar 2014
 */
public class Vertex {
	private int x; // position in x-axe
	private int y; // position in y-axe

	/**
	 * Constructor
	 * 
	 * @param x
	 *            - position in x-axe
	 * @param y
	 *            - position in y-axe
	 */
	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter
	 * 
	 * @return vertex position in x-axe
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter
	 * 
	 * @return vertex position in y-axe
	 */
	public int getY() {
		return y;
	}
}
