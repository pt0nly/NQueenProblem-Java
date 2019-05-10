import java.util.Arrays;
import java.util.Iterator;

public class Stack<T> implements Iterable<T> {
	private T[] items;
	private int size;
	
	@SuppressWarnings("unchecked")
	public Stack(){
		items = (T[]) new Object[1];
		size = 0;    
	}
	
	private void resize(int capacity){
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[capacity];
		for (int i = 0; i < size; i++)
			temp[i] = items[i];
		items = temp;
	}
	
	/*
	 * void push(T x)
	 * adds a value to the top of the stack
	 */
	public void push(T x){
		if (size == items.length)
			resize(2 * items.length);
		items[size++] = x;
	}
	
	/*
	 * boolean isEmpty()
	 * returns if the stack is empty
	 */
	public boolean isEmpty(){
		return size == 0;
	}
	
	/*
	 * int size()
	 * returns the size of the stack
	 */
	public int size(){
		return size;
	}
	
	/*
	 * T pop()
	 * returns the value from the top of the stack, and remove it from the stack
	 */
	public T pop(){
		T result = items[--size];
		items[size] = null;
		if (size > 0 && size == items.length / 4)
			resize(items.length / 2);
		return result;
	}
	
	/*
	 * T top()
	 * returns the value from the top of the stack without removing it
	 */
	public T top(){
		return items[size-1];
	}
	
	/*
	 * This is the stack Iterator
	 */
	private class StackIterator implements Iterator<T>{
		private int i = size;

		public boolean hasNext(){
			return i > 0;
		}

		public T next(){
			return items[--i];
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}

	}
	
	@Override
	public Iterator<T> iterator() {
		return new StackIterator();
	}
	
	/*
	 * int capacity()
	 * returns the capacity of the stack
	 */
	public int capacity(){
		return items.length;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(items);
	}
}
