/*
Program #4
CS310
Hashim Abdirahim
cssc1449
*/
package data_structures;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BalancedTree<K extends Comparable<K>,V> implements DictionaryADT<K,V> {
	
	TreeMap<K,V> tree;
	
	public BalancedTree() {
		tree = new TreeMap<K,V>();
	}
	
	public boolean contains(K key) {
		return tree.containsKey(key);
	}

	public boolean add(K key, V value) {
		return tree.put(key,value) == null;
	}

	public boolean delete(K key) {
		return (tree.remove(key) != null);
	}
	
	public V getValue(K key) {
		return tree.get(key);
	}

	public K getKey(V value) {
		for(Entry<K,V> X : tree.entrySet())
			if(((Comparable<V>)value).compareTo(X.getValue()) == 0)
				return X.getKey();
		return null;
	}
	
	public int size() {
		return tree.size();
	}

	public boolean isFull() {
		return false;
	}
	
	public boolean isEmpty() {
		return tree.isEmpty();
	}

	public void clear() {
		tree.clear();
	}

	public Iterator<K> keys() {
		return tree.navigableKeySet().iterator();
	}
	
	public Iterator<V> values() {
		return tree.values().iterator();
	}

}
