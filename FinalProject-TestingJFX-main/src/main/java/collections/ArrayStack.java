package collections;

import java.util.*;


/**
 *Datastruktur som lagrar som stack alltså enligt Lifo.
 * 
 * @author Erfan Tavoosi
 *
 */
public class ArrayStack<T> implements Stack<T>
{
	private T[] elements;
	private int size = 0;

	/**
	 * Konstruktor
	 * 
	 * @param capacity
	 */
	public ArrayStack(int capacity)
	{
		elements = (T[]) (new Object[capacity]);
	}

	/**
	 * Läggs sista lediga platsen
	 * 
	 * @param element
	 *            
	 */
	public void push(T element)
	{
		if (size >= elements.length)
			throw new StackOverflowException();
		elements[size] = element;
		size++;
	}

	/**
	 * Returnerar det objektet som lades till sist och tar bort det
	 * 
	 * @return sista elementet
	 */
	public T pop()
	{
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		T elem = elements[--size];
		elements[size] = null;
		return elem;
	}

	/**
	 * Returnerar det sista objektet utan att raderar det.
	 * 
	 * @return the element that was last placed on the stack.
	 */
	public T peek()
	{
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		return elements[size - 1];
	}

	/**
	 * Tar reda på om stacken är tom
	 * 
	 * @return true if stack is empty, otherwise false
	 */
	public boolean isEmpty()
	{
		return (size == 0);
	}

	/**
	 * Returnerar antalet object i stacken
	 * 
	 * @return antalet object i stackenk
	 */
	public int size()
	{
		return size;
	}

}
