package collections;

import java.io.ObjectInputStream.GetField;
import java.util.Iterator;

import javax.swing.text.StyledEditorKit.ForegroundAction;



/**
 * En länkadlista som används för att lagra data i en lista där varje nod pekar på nästa nod i listan.
 * 
 * @author Erfan Tavoosi
 *
 */
public class LinkedList<E> implements List<E>, Iterable<E>
{
	private ListNode<E> theList = null;
	private ListNode<E> theFisrtNode = null;
	int size=0;

	/**
	 * Returnerar noden på index.
	 * 
	 * @param index
	 *           
	 * @return noden
	 */
	private ListNode<E> locate(int index)
	{
		ListNode<E> node = theList;
		for (int i = 0; i < index; i++)
			node = node.getNext();
		return node;
	}

	/**
	 * Antalet noder
	 * 
	 * @return Antalet noder 
	 */
	public int size()
	{
		int n = 0;
		ListNode<E> node = theList;
		while (node != null)
		{
			node = node.getNext();
			n++;
		}
		return n;
	}

	/**
	 * Hittar noden på indexet
	 * 
	 * @param index
	 * 
	 * @return noden
	 */
	public E get(int index)
	{
		if ((index < 0) || (index >= size))
			throw new IndexOutOfBoundsException("size=" + size + ", index=" + index);

		ListNode<E> node = locate(index);
		return node.getData();
	}

	/**
	 * Ändrar värdet på den specifika platsen och radera och returnerar gamla
	 * 
	 * @return gamla värdet
	 */
	public E set(int index, E element)
	{
		ListNode<E> changedNode = locate(index);
		E oldValue = changedNode.getData();
		changedNode.setData(element);
		return oldValue;
	}

	/**
	 * Lägger till elementet element
	 * 
	 * @param element
	 *            
	 */
	public void add(E element)
	{
		add(size, element);
		
	}

	/**
	 * Lägger till elementet först
	 * 
	 * @param element
	 *            lägs först
	 */
	public void addFirst(E element)
	{
		add(0, element);
		
	}

	/**
	 * Lägget elementet sist
	 * 
	 * @param element
	 *            Lägs sist
	 */
	public void addLast(E element)
	{
		add(size, element);
		
	}

	/**
	 * Lägger till en nytt element på platsen index
	 * 
	 * @param index
	 *            platsen för elementet
	 * @param element
	 *            elementet
	 */
	public void add(int index, E element)
	{
		
		
			
		if ((index < 0) || (index > size))
		{
			throw new IndexOutOfBoundsException("size=" + size + ", index=" + index);
		}
		if (index == 0)
		{
			theList = new ListNode<E>(element, locate(0));
			theFisrtNode = theList;
			
		} else
		{
			ListNode<E> prevNode = locate(index - 1);
			ListNode<E> nextNode = locate(index);
			ListNode<E> newNode = new ListNode<E>(element, nextNode);
			prevNode.setNext(newNode);
		}
		size++;
	}

	/**
	 * Tar bort sista elemnentet
	 * 
	 * @return borttagna elemnentet
	 */
	public E removeLast()
	{
		E removed = remove(size - 1);
		
		return removed;
	}
	
	/**
	 * Tarbort första elementet
	 * 
	 * @return det borttagna elementet.
	 */
	public E removeFirst()
	{
		E removed = remove(0);
		
		return removed;
	}

	/**
	 *Tar bort elementet på specififik index
	 * 
	 * @param index
	 *            platsen för elementet som ska bort
	 * @return bortagna elementet
	 */
	public E remove(int index)
	{
		if ((index < 0) || (index >= size))
			throw new IndexOutOfBoundsException("size=" + size + ", index=" + index);

		E res;
		if (index == 0)
		{
			res = theList.getData();
			theList = theList.getNext();
			theFisrtNode=theList;
			//theList = setNull(theList);
		
		} else
		{
			ListNode<E> node = locate(index - 1);
			res = node.getNext().getData();
			node.setNext(setNull(node.getNext()));
			// node.setNext( node.getNext().getNext() );
		}
		size--;
		return res;
	}

	private ListNode<E> setNull(ListNode<E> toNull)
	{
		ListNode<E> res = toNull.getNext();
		toNull.setData(null);
		toNull.setNext(null);
		return res;
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
		int index = indexOf(0, element);
		return index;
	}

	/**
	 * 
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
		if ((startIndex < 0) || (startIndex > size))
		{
			throw new IndexOutOfBoundsException("size=" + size + ", index=" + startIndex);
		}
		for (int i = startIndex; i < size; i++)
		{
			if (locate(i).getData().equals(element))
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Tar bort allt
	 */
	public void clear()
	{
		ArrayStack<E> stack = new ArrayStack<E>(size);
		for (int i = 0; i < size; i++)
		{
			stack.push((E) locate(i));
		}
		
		for (int i = 0; i < size; i++)
		{
			ListNode<E> element = (ListNode<E>) stack.pop();
			element.setData(null);
			element.setNext(null);
		}
		size=0;
		theList = null;
	}
	
	public void restartList()
	{
		theList=theFisrtNode;
	}
	
	
	/**
	 * Kallar på ListNodes toString
	 * 
	 * @return Listnodes toString
	 * 
	 */
	public String toString()
	{
		if (theList != null)
			return theList.toString();
		else
			return "[]";
	}

	/**
	 * Ger iteratorn
	 * 
	 * @return iterator
	 */
	public Iterator<E> iterator()
	{
		return new Iter();
	}

	/**
	 * Används för iterera datastrukturen
	 * 
	 * @author Erfan Tavoosi
	 *
	 */
	private class Iter implements Iterator<E>
	{

		public boolean hasNext()
		{
			if (theList == null)
			{
				restartList();
				return false;
			}
			return true;
		}

		public E next()
		{
			ListNode<E> temp = theList;
			theList = theList.getNext();
			return temp.getData();
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}
		
	
	}

}
