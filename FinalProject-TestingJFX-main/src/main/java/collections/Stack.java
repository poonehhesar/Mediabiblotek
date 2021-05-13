package collections;

public interface Stack<T>
{
	/**
	 * Placerar ett element i stacken.
	 * 
	 * @param element
	 *            elementet att lägga på stacken
	 */
	public void push(T element);

	/**
	 * Returnerar det element som senast placerades i stacken. Elementet tas
	 * bort från stacken.
	 * 
	 * @return det element som senast placerades i stacken
	 */
	public T pop();

	/**
	 * Returnerar det element som senast placerade i stacken. Elementet är kvar
	 * i stacken.
	 * 
	 * @return det element som senast placerades i stacken
	 */
	public T peek();

	/**
	 * Returnerar true om stacken inte innehåller några element och false om det
	 * finns element i stacken.
	 * 
	 * @return true om stacken inte innehåller några element och annars false
	 */
	public boolean isEmpty();

	/**
	 * Returnerar antalet element som finns i stacken.
	 * 
	 * @return antalet element som finns i stacken
	 */
	public int size();
}
