/*
Program #2
CS310
Hashim Abdirahim
cssc1449
*/
package data_structures;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class LinearList<E extends Comparable<E>> implements LinearListADT<E> {

	private int currentSize, modCount;
	private Node<E> head,tail;
	
	public LinearList() {
		currentSize = 0;
		head = tail = null;
	}
	
	
	private class Node<E>{
		E data;
		Node<E> next;
		Node<E> previous;
		
		public Node(E obj){
			this.data = obj;
			next = previous = null;
		}
	}
	
	
	public boolean addFirst(E obj) {
		if (isEmpty()) {
			head = tail = new Node<E>(obj);
			currentSize++;
			modCount++;
			return true;	
		}
		Node<E> temp = new Node<E>(obj);
		temp.next = head;
		head = temp;
		head.next.previous = head;
		currentSize++;
		modCount++;
		return true;
	}

	
	public boolean addLast(E obj) {
		if (isEmpty()) {
			head = tail = new Node<E>(obj);
			currentSize++;
			modCount++;
			return true;
		}
		Node<E> temp = new Node<E>(obj);
		temp.previous = tail;
		tail = temp;
		tail.previous.next = tail;
		currentSize++;
		modCount++;
		return true;
	}
	

	public E removeFirst() {
		if(isEmpty())
			return null;
		E temp;
		temp = head.data;
		if(currentSize == 1) {
			head = tail = null;
			currentSize--;
			modCount++;
			return temp;
		}
		head = head.next;
		head.previous = null;
		currentSize--;
		modCount++;
		return temp;
	}

	
	public E removeLast() {
		if(isEmpty()) 
			return null;
		E temp;
		temp = tail.data;
		if (currentSize == 1) {
			head = tail = null;
			currentSize--;
			modCount++;
			return temp;
		}
		tail = tail.previous;
		tail.next = null;
		currentSize--;
		modCount++;
		return temp;
	}

	
	public E remove(E obj) {
		Node<E> current = head;
		Node<E> previous = null;
		while (current != null) {
			if (((Comparable<E>) current.data).compareTo(obj) == 0) {
				if(current == head)
					return removeFirst();
				if(current == tail)
					return removeLast();
				currentSize--;
				previous.next = current.next;
				previous.next.previous = previous;
				modCount++;
				return current.data;
			}
			previous = current;
			current = current.next;
		}
		
		return null;
	}
	

	public E peekFirst() {
		if(head == null)
			return null;
		return (E) head.data;
	}

	
	public E peekLast() {
		if (tail == null)
			return null;
		return tail.data;
	}
	

	public boolean contains(E obj) {
		return (find(obj) != null);
	}
	

	public E find(E obj) {
		Node<E> current = head;
		while (current != null) {
			if (((Comparable<E>) current.data).compareTo(obj) == 0) 
				return current.data;
			current = current.next;
		}
		return null;
	}

	
	public void clear() {
		currentSize = 0;
		head = tail = null;	
		modCount++;
	}

	
	public boolean isEmpty() {
		return currentSize == 0;
	}


	public boolean isFull() {
		return false;
	}
	

	public int size() {
		return currentSize;
	}
	
	
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	private class IteratorHelper implements Iterator<E> {
		Node<E> index = head;
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
				obj = index.data;
				index = index.next;
				count++;
				return obj;
				
			}

		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
