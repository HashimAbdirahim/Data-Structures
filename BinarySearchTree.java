/*
Program #4
CS310
Hashim Abdirahim
cssc1449
*/
package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinarySearchTree<K extends Comparable<K>, V> implements DictionaryADT<K, V> {

	private Node<K, V> root;
	private int currentSize, modCounter;
	private K k;

	public BinarySearchTree() {
		root = null;
		currentSize = 0;
		modCounter = 0;
		k = null;
	}
	private class Node<K, V> {
		private K key;
		private V value;
		private Node<K, V> leftChild, rightChild;

		public Node(K k, V v) {
			key = k;
			value = v;
			leftChild = rightChild = null;
		}
	}
	
	public boolean contains(K key) {
		return findValue(key, root) != null;
	}

	// refrenced Riggins reader
	public boolean add(K key, V value) {
		if (contains(key))
			return false;
		if (root == null)
			root = new Node<K, V>(key, value);
		else
			insert(key, value, root, null, false);
		currentSize++;
		modCounter++;
		return true;
	}

	public boolean delete(K key) {
		if (isEmpty())
			return false;
		if(!remove(key,root,null,false))
			return false;
		currentSize--;
		modCounter++;
		return true;
	}
	
	public V getValue(K key) {
		if (isEmpty())
			return null;
		return findValue(key, root);
	}
	
	public K getKey(V value) {
		if (isEmpty())
			return null;
		k = null;
		findKey(root, value);
		return k;
	}

	public int size() {
		return currentSize;
	}

	public boolean isFull() {
		return false;
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	public void clear() {
		root = null;
		currentSize = 0;
		modCounter++;
	}
/////// Iterator Helper
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected int i;
		protected int j;
		private int modCount;
		Node<K,V>[] orderedArray;
		
		public IteratorHelper(){
			i = j = 0;
			modCount = modCounter;
			orderedArray = new Node[currentSize];
			inOrder(root);
		}
		
		public void inOrder(Node<K, V> n) {
			if(n == null)
				return;
			inOrder(n.leftChild);
			orderedArray[j++] = root;
			inOrder(n.rightChild);
		}

		public boolean hasNext(){
			if(modCount != modCounter)
				throw new ConcurrentModificationException();
			return i < currentSize;	
		}
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
	
	}
	class KeyIteratorHelper<K> extends IteratorHelper<K>{

		public KeyIteratorHelper() {
			super();
		}
		
		public K next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return (K) orderedArray[i++].key;
		}
		
	}
	class ValueIteratorHelper<V> extends IteratorHelper<V>{
		
		public ValueIteratorHelper(){
			super();
		}
		
		public V next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return (V) orderedArray[i++].value;
		}
	
	}
		
	public Iterator<K> keys() {
		return new KeyIteratorHelper();
	}

	public Iterator<V> values() {
		return new ValueIteratorHelper();
	}
	
	
	////////HELPER METHODS
	//Refrenced Rigins
	private void insert(K k, V v, Node<K, V> n, Node<K, V> parent, boolean wasLeft) {
		if (n == null) { 
			if (wasLeft)
				parent.leftChild = new Node<K, V>(k, v);
			else
				parent.rightChild = new Node<K, V>(k, v);
		} else if (((Comparable<K>) k).compareTo((K) n.key) < 0)
			insert(k, v, n.leftChild, n, true); 
		else
			insert(k, v, n.rightChild, n, false); 
	}
	
	// Worked From riggins reader
	private V findValue(K key, Node<K, V> n) {
			if (n == null)
				return null;
			if (((Comparable<K>) key).compareTo(n.key) < 0)
				return findValue(key, n.leftChild);
			else if (((Comparable<K>) key).compareTo(n.key) > 0)
				return findValue(key, n.rightChild);
			else
				return (V) n.value;
		}
		
	private void findKey(Node<K,V> n, V value){
			if(n==null) return;
			if(((Comparable<V>)value).compareTo(n.value) == 0){
				k=n.key;
				return;
				}
			findKey(n.leftChild,value);
			findKey(n.rightChild,value);
		}
	
	private boolean remove(K key, Node<K,V> n, Node<K,V> parent, boolean wasLeft) {
		if(n == null)
			return false;
		int compare = 0;
		compare = ((Comparable<K>)key).compareTo(n.key);
		if(compare < 0)
			return remove(k, n.leftChild, n, true);
		else if(compare >0)
			return remove(k, n.rightChild, n, false);
		else {
			if((n.leftChild == null) && n.rightChild == null) {
				if(parent == null)
					root = null;
				else if(wasLeft)
					parent.leftChild = null;
				else
					parent.rightChild = null;
			}
			else if(n.leftChild == null) {
				if (parent == null)
					root = n.rightChild;
				else if(wasLeft)
					parent.leftChild = n.leftChild;
				else
					parent.rightChild = n.leftChild;
			}
			else {
				Node<K,V> tmp = Successor(n.rightChild);
				if(tmp == null) {
					n.key = n.rightChild.key;
					n.value = n.rightChild.value;
					n.rightChild = n.rightChild.rightChild;		
				}
				else {
					n.key = tmp.key;
					n.value = tmp.value;
				}
			}
		}
		return true;
	}
	
	private Node<K,V> Successor(Node<K,V> n){
    	Node<K,V> parent = null;
    	while(n.leftChild != null){
    		parent = n;
    		n = n.leftChild;
    	}
    	if(parent == null)
    		return null;
    	else
    		parent.leftChild = n.rightChild;
    	return n;
    }
	
}