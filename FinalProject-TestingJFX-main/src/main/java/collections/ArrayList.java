
package collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * En generisk datastruktur som används för att lagra data. E är typen på datan
 * som lagras. Datan sparas i en array.
 * 
 * 
 * @author Erfan Tavoosi
 *
 */

public class ArrayList<E> implements List<E>
{
	private E[] elements;
	private int size;

	/**
	 * Kosntruktorn sätter default värdet till 30.
	 */
	public ArrayList()
	{
		this(30);
	}

	/**
	 * Konstruktorn sätter storleken på arrayn
	 * 
	 * @param arraySize
	 *            storleken
	 */
	public ArrayList(int arraySize)
	{
		arraySize = Math.max(1, arraySize);
		elements = (E[]) new Object[arraySize];
	}
	
	public void sortByTitle()
	{
		
	}

	/**
	 * Förstorar arrayn
	 */
	private void grow()
	{
		E[] temp = (E[]) new Object[size * 2];
		for (int i = 0; i < elements.length; i++)
		{
			temp[i] = elements[i];
		}
		this.elements = temp;
	}

	/**
	 * Lägger till element på specifik index
	 * 
	 * @param index
	 *            Där elementet ska sparas
	 * @param theElement
	 *            elementet som ska sparas
	 */
	public void add(int index, E theElement)
	{
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		if (size == elements.length)
			grow();
		for (int i = size; i > index; i--)
		{
			elements[i] = elements[i - 1];
		}
		elements[index] = theElement;
		size++;
	}

	/**
	 * Lägger elementet i slutet
	 * 
	 * @param element
	 *            elementet som lägs till
	 */
	public void add(E element)
	{
		add(size, element);
	}

	/**
	 * Lägger till elementet först
	 * 
	 * @param element
	 *            elementet som lägs till
	 */
	public void addFirst(E element)
	{
		add(0, element);
	}

	/**
	 * Lägger till elemntet sist
	 * 
	 * @param element
	 *            elementet som lägs till
	 */
	public void addLast(E element)
	{
		add(size, element);
	}

	/**
	 * Tar bort elementet på given index. Returnerar elementet som tas bort.
	 * 
	 * @param index
	 *            pekar på elementet som ska tas bort
	 * @return returnerar elementet som tas bort
	 */
	public E remove(int index)
	{
		if (index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException();
		}
		E element = elements[index];
		size--;
		for (int i = index; i < size; i++)
		{
			elements[i] = elements[i + 1];
		}
		return element;
	}

	/**
	 * Tar bort och returnerar det första elementet 
	 * 
	 * @return elementet som tas bort.
	 */
	public E removeFirst()
	{
		E element = elements[0];
		remove(0);
		return element;
	}

	/**
	 * Tar bort det sista elementet
	 * 
	 * @return elementet av typen E som tas bort.
	 */
	public E removeLast()
	{
		E element = elements[size - 1];
		remove(size - 1);
		return element;
	}

	/**
	 *Tar bort alla element.
	 */
	public void clear()
	{
		for (int i = 0; i < elements.length; i++)
		{
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Hämtar elementet på platsen 'index'
	 * 
	 * @param index
	 *            platsen på elementet
	 * @return elementet på platsen index
	 */
	public E get(int index)
	{
		if (index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}

	/**
	 * Sätter elementet på platsen index. Det vill säga skriver över det gamla värdet
	 * 
	 * @param index
	 *            indexet för elementet
	 * @param element
	 *            elementet som ska sparas
	 * @return returnerar det gamla elementet
	 */
	public E set(int index, E element)
	{
		if (index < 0 || index > size)
		{

			throw new IndexOutOfBoundsException();
		}
		E prevElem = remove(index);
		add(index, element);
		return prevElem;
	}
	
	

	public E[] getElements()
	{
		return elements;
	}

	public void setElements(E[] elements)
	{
		this.elements = elements;
	}

	/**
	 * Returnerar det senaste förekommande indexet som på det givna elementet
	 * 
	 * @param element
	 *            elementet
	 * @return indexet på elementet
	 */
	public int indexOfFirst(E element)
	{
		return indexOf(0, element);

	}

	/**
	 *Returnerar det senaste förekommande indexet som på det givna elementet. Sökningen börjar vid startIndex.
	 * 
	 * @param startIndex
	 *           sökningen startar
	 * @param element
	 *            elementet
	 * @return indexet på elementet
	 */
	public int indexOf(int startIndex, E element)
	{
		if (startIndex < 0 || startIndex > size)
		{
			throw new IndexOutOfBoundsException();
		}
		for (int i = startIndex; i < size; i++)
		{
			if (get(i).equals(element))
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * Antalet element på listan
	 * 
	 * @return Antalet element på lista
	 */
	public int size()
	{
		return size;
	}

	/**
	 * Gör om till sträng genom att lägga till alla element som strängar. Så att det blir en enda sträng.
	 * 
	 * @return strängen
	 */
	public String toString()
	{
		StringBuilder res = new StringBuilder("[ ");
		for (int i = 0; i < size; i++)
		{
			res.append(elements[i]);
			if (i < size - 1)
				res.append("; ");
		}
		res.append(" ]");
		return res.toString();
	}

	/**
	 * Ger iteratorn
	 * 
	 * @return iterator
	 */
	public Iterator<E> iterator()
	{
		return new MyIterator();
	}

	/**
	 * Används för iterera datastrukturen
	 * 
	 * @author Erfan Tavoosi
	 *
	 */
	private class MyIterator implements Iterator<E>
	{
		private int index = 0;

	
		public boolean hasNext()
		{
			return index < size;
		}

	
		public E next()
		{
			if (index == size)
				throw new NoSuchElementException();
			return elements[index++];
		}

	
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

}
