import java.util.Iterator;

public class Bag<T> implements Iterable<T> {
	private T[] items;
	private int size;
	
	@SuppressWarnings("unchecked")
	public Bag(){
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

	public void add(T x){
		if (size == items.length)
			resize(2 * items.length);
		items[size++] = x;
	}

	public boolean isEmpty(){
		return size == 0;
	}

	public int size(){
		return size;
	}

	private class BagIterator implements Iterator<T>{
		private int i = 0;

		public boolean hasNext(){
			return i < size;
		}

		public T next(){
			return items[i++];
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
	
	public T[] getItems() {
		return this.items;
	}

	public Iterator<T> iterator(){
		return new BagIterator();
	}
}
