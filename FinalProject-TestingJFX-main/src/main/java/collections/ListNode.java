package collections;

/**
 * 
 * Symboliserar ett objekt i en lista, används för att bygga länkadelistor.
 * 
 * @author Erfan Tavoosi
 *
 * @param <E>
 */
public class ListNode<E>
{
	private E theData;
	private ListNode<E> theNextOne;

	/**
	 * Konstruktor
	 * 
	 * @param parData
	 *            Datat
	 * @param parNext
	 *            Nästa objekt i listan
	 */
	public ListNode(E parData, ListNode<E> parNext)
	{
		theData = parData;
		theNextOne = parNext;
	}

	/**
	 * Returnerar datat
	 * 
	 * @return datat
	 */
	public E getData()
	{
		return theData;
	}

	/**
	 * Sätter datavärdet
	 * 
	 * @param parData
	 *            
	 */
	public void setData(E parData)
	{
		theData = parData;
	}

	/**
	 * Returnerar nästa objekt
	 * 
	 * @return Nästa objekt
	 */
	public ListNode<E> getNext()
	{
		return theNextOne;
	}

	/**
	 * Sätter nästa objekt.
	 * 
	 * @param parNext
	 *           
	 */
	public void setNext(ListNode<E> parNext)
	{
		theNextOne = parNext;
	}

	/**
	 * Gör om till sträng genom att lägga till alla element som strängar. Så att det blir en enda sträng.
	 * 
	 * @return str.toString , the appended String
	 */
	public String toString()
	{
		StringBuilder str = new StringBuilder("");
		str.append(theData.toString());
		ListNode<E> node = theNextOne;
		while (node != null)
		{
			str.append(" - ");
			str.append(node.getData().toString());
			node = node.getNext();
		}
		//str.append(" ]");
		return str.toString();
	}
}