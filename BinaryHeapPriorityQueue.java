/*
Program #3
CS310
Hashim Abdirahim
cssc1449
*/
package data_structures;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class BinaryHeapPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {

	private Wrapper<E>[] storage;
	private long entryNumber;
	private int currentSize, maxSize, modCount;

	protected class Wrapper<E> implements Comparable<Wrapper<E>> {
		long number;
		E data;

		public Wrapper(E obj) {
			number = entryNumber++;
			data = obj;
		}

		public int compareTo(Wrapper<E> o) {
			if (((Comparable<E>) data).compareTo(o.data) == 0)
				return (int) (number - o.number);
			return ((Comparable<E>) data).compareTo(o.data);
		}
	}

	public BinaryHeapPriorityQueue(int size) {
		maxSize = size;
		currentSize = 0;
		modCount = 0;
		entryNumber = 0;
		storage = new Wrapper[maxSize];

	}

	public BinaryHeapPriorityQueue() {
		this(DEFAULT_MAX_CAPACITY);
	}

	public boolean insert(E object) {
		if (isFull()) {
			return false;
		}
		Wrapper<E> newItem = new Wrapper<E>(object);
		storage[currentSize] = newItem;
		currentSize++;
		modCount++;
		trickleUp();
		return true;
	}

	public E remove() {
		if (isEmpty())
			return null;
		E tmp = storage[0].data;
		storage[0] = storage[currentSize - 1];
		currentSize--;
		modCount++;
		trickleDown();
		return tmp;

	}

	public boolean delete(E obj) {
		boolean found = false;
		BinaryHeapPriorityQueue<E> tmpHeap = new BinaryHeapPriorityQueue<E>(maxSize);
		for(int i =0;i < currentSize;i++) {
			if(obj.compareTo(storage[i].data) != 0){
                tmpHeap.insert(storage[i].data);
            }
			else {
				found = true;
				modCount++;
			}
		}
		if(found) {
			storage = tmpHeap.storage;
	        currentSize = tmpHeap.currentSize;
	        entryNumber = tmpHeap.entryNumber;
		}
		return found;
	}

	public E peek() {
		if (isEmpty())
			return null;
		return storage[0].data;
	}

	public boolean contains(E obj) {
		for (int i = 0; i < currentSize; i++) {
			if (obj.compareTo(storage[i].data) == 0)
				return true;
		}
		return false;
	}

	public int size() {
		return currentSize;
	}

	public void clear() {
		currentSize = 0;
		modCount++;

	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public boolean isFull() {
		return currentSize == maxSize;
	}

	public Iterator<E> iterator() {
		return new IteratorHelper();
	}

	private class IteratorHelper implements Iterator<E> {

		E[] auxArr;
		int index;
		int modification;

		public IteratorHelper() {
			auxArr = (E[]) new Comparable[currentSize];
			index = 0;
			modification = modCount;
			for (int i = 0; i < currentSize; i++) {
				auxArr[i] = storage[i].data;
			}
			selectionSort(auxArr);

		}

		public boolean hasNext() {
			return index != currentSize;
		}

		public E next() {
			E obj;
			if (modification != modCount) {
				throw new ConcurrentModificationException();
			} else if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				obj = auxArr[index++];
				return obj;

			}

		}

	}
	//refrence Riggins
	private void trickleUp() {
		int newIndex = currentSize - 1;
		int parentIndex = (newIndex - 1) >> 1;
		Wrapper<E> newValue = storage[newIndex];
		while (parentIndex >= 0 && newValue.compareTo(storage[parentIndex]) < 0) {
			storage[newIndex] = storage[parentIndex];
			newIndex = parentIndex;
			parentIndex = (parentIndex - 1) >> 1;
		}
		storage[newIndex] = newValue;
	}
	//refrence Riggins
	private void trickleDown() {
		int current = 0;
		int child = getNextChild(current);
		Wrapper<E> tmp;
		while (child != -1 && storage[current].compareTo(storage[child]) > 0) {
			tmp = storage[current];
			storage[current] = storage[child];
			storage[child] = tmp;
			current = child;
			child = getNextChild(current);
		}
	}
	//reference Riggins
	private int getNextChild(int current) {
		int left = (current << 1) + 1;
		int right = left + 1;
		if (right < currentSize) {
			if (storage[left].compareTo(storage[right]) < 0)
				return left;
			return right;
		}
		if (left < currentSize)
			return left;
		return -1;
	}

	private void selectionSort(E arr[]) {
		int n = arr.length;

		for (int i = 0; i < n - 1; i++) {
			int min_idx = i;
			for (int j = i + 1; j < n; j++)
				if (arr[j].compareTo(arr[min_idx]) < 0)
					min_idx = j;
			E temp = arr[min_idx];
			arr[min_idx] = arr[i];
			arr[i] = temp;
		}
	}

}
