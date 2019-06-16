/*
Program #1
CS310
Hashim Abdirahim
cssc1449
*/
package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class ArrayLinearList<E> implements LinearListADT1<E> {
	private int currentSize, maxSize, front, rear, modCount;
	private E[] storage;

	@SuppressWarnings("unchecked")
	public ArrayLinearList(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("size too small!!");
		} else {
			maxSize = size;
			currentSize = 0;
			front = 0;
			rear = 0;
			modCount = 0;
			storage = (E[]) new Object[maxSize];
		}
	}

	public ArrayLinearList() {
		this(DEFAULT_MAX_CAPACITY);
	}

	public void ends() {
		System.out.println("Front: " + front + " Rear: " + rear);

	}

	public boolean addFirst(E obj) {
		boolean response;
		if (currentSize == maxSize) {
			response = false;
		} else if (maxSize == 1) {
			storage[front] = obj;
			currentSize += 1;
			modCount += 1;
			response = true;
		} else if (currentSize < 1) {
			storage[front] = obj;
			currentSize += 1;
			modCount += 1;
			response = true;
		} else if (front == 0) {
			front = (maxSize - 1);
			storage[front] = obj;
			currentSize += 1;
			modCount += 1;
			response = true;
		} else {
			storage[--front] = obj;
			currentSize += 1;
			modCount += 1;
			response = true;
		}

		return response;
	}

	public boolean addLast(E obj) {
		boolean response;
		if (currentSize == maxSize) {
			response = false;
		} else if (maxSize == 1) {
			storage[rear] = obj;
			currentSize += 1;
			modCount += 1;
			response = true;
		} else if (currentSize < 1) {
			storage[rear] = obj;
			currentSize += 1;
			modCount += 1;
			response = true;
		} else if (rear == (maxSize - 1)) {
			rear = 0;
			storage[rear] = obj;
			currentSize += 1;
			modCount += 1;
			response = true;
		} else {
			storage[++rear] = obj;
			currentSize += 1;
			modCount += 1;
			response = true;
		}

		if (currentSize == 1 || currentSize == 0)
			front = rear;

		return response;

	}

  public E removeFirst() {
		E temp;
		if (currentSize == 0)
			return null;
		else if (maxSize == 1) {
			temp = storage[front];
			storage[front] = null;
			currentSize = 0;
			modCount += 1;
			return temp;
		} else if (currentSize == 1) {
			temp = storage[front];
			storage[front] = null;
			front = rear;
			currentSize -= 1;
			modCount += 1;
			return temp;
		} else if (front == (maxSize - 1)) {
			temp = storage[front];
			storage[front] = null;
			currentSize -= 1;
			front = 0;
			modCount += 1;
			return temp;
		} else {
			temp = storage[front];
			storage[front++] = null;
			currentSize -= 1;
			modCount += 1;
			return temp;
		}

	}

	public E removeLast() {
		E temp;
		if (currentSize == 0)
			return null;
		else if (maxSize == 1) {
			temp = storage[rear];
			storage[rear] = null;
			currentSize = 0;
			modCount += 1;
			return temp;
		} else if (currentSize == 1) {
			temp = storage[rear];
			storage[rear] = null;
			rear = front;
			currentSize -= 1;
			modCount += 1;
			return temp;

		} else if (rear == 0) {
			temp = storage[rear];
			storage[rear] = null;
			currentSize -= 1;
			rear = (maxSize - 1);
			modCount += 1;
			return temp;
		} else {
			temp = storage[rear];
			storage[rear--] = null;
			currentSize -= 1;
			modCount += 1;
			return temp;
		}
	}

	@SuppressWarnings("unchecked")
	public E remove(E obj) {
		if (currentSize == 0)
			return null;

		int index = front;

		while (((Comparable<E>) storage[index]).compareTo(obj) != 0) {
			if (front == rear)
				return null;
			index = index + 1;

			if (index == maxSize)
				index = 0;
		}

		while (index != rear) {
			if (index == (maxSize - 1)) {
				storage[index] = storage[0];
				index = 0;
			} else {
				storage[index] = storage[index + 1];
				index++;
			}
		}
		currentSize -= 1;
		rear -= 1;
		if (rear < 0)
			rear = maxSize - 1;
		
		modCount++;
		return obj;

	}

	public E peekFirst() {
		E obj;
		if (currentSize == 0)
			return null;
		else {
			obj = storage[front];
			return obj;
		}

	}

	public E peekLast() {
		E obj;
		if (currentSize == 0)
			return null;
		else {
			obj = storage[rear];
			return obj;
		}
	}

	public boolean contains(E obj) {
		if (find(obj) == null)
			return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	public E find(E obj) {
		for (E x : storage)
			if (((Comparable<E>) obj).compareTo(x) == 0)
				return x;
		return null;

	}

	public void clear() {
		currentSize = 0;
		front = 0;
		rear = 0;
		modCount += 1;
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public boolean isFull() {
		return currentSize == maxSize;
	}

	public int size() {
		return currentSize;
	}

	public Iterator<E> iterator() {
		return new IteratorHelper();

	}

	private class IteratorHelper implements Iterator<E> {
		int index = front;
		int count = 0;
		int modification = modCount;

		// check if there's element in the next position
		public boolean hasNext() {
			return count != currentSize;
		}

		// returns the current index and increments the index
		public E next() {
			E obj;
			if (modification != modCount) {
				throw new ConcurrentModificationException();
			} else if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				obj = storage[index++];
				if (index == maxSize) {
					index = 0;
				}
				count++;
				return obj;
			}

		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
