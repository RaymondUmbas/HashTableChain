package edu.miracosta.cs113;
import java.util.*;

/**
 * HashTable implementation using chaining to tack a pair of key and value pairs.
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class HashTableChain<K, V> implements Map<K, V>  {

    private LinkedList<Entry<K, V>>[] table ;
    private  int numKeys ;
    private static final int CAPACITY = 101 ;
    private static final double LOAD_THRESHOLD = 1.5 ;

    ///////////// ENTRY CLASS ///////////////////////////////////////

    /**
     * Contains key-value pairs for HashTable
     * @param <K> the key
     * @param <V> the value
     */
    private static class Entry<K, V> implements Map.Entry<K, V>{
        private K key ;
        private V value ;

        /**
         * Creates a new key-value pair
         * @param key the key
         * @param value the value
         */
        public Entry(K key, V value) {
            this.key = key ;
            this.value = value ;
        }

        /**
         * Returns the key
         * @return the key
         */
        public K getKey() {
            return  key;
        }

        /**
         * Returns the value
         * @return the value
         */
        public V getValue() {
            return value ;
        }

        /**
         * Sets the value
         * @param val the new value
         * @return the old value
         */
        public V setValue(V val) {
            V oldVal = value;
            value = val ;
            return oldVal ;
        }
        @Override
        public String toString() {
            return  key + "=" + value  ;
        }

        public int hash() {
        	return key.hashCode();
        }

    }

    ////////////// end Entry Class /////////////////////////////////

    ////////////// EntrySet Class //////////////////////////////////

    /**
     * Inner class to implement set view
     */
    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {


        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new SetIterator();
        }

        @Override
        public int size() {
            return numKeys ;
        }
    }

    ////////////// end EntrySet Class //////////////////////////////

    //////////////   SetIterator Class ////////////////////////////

    /**
     * Class that iterates over the table. Index is table location
     * and lastItemReturned is entry
     */
    private class SetIterator implements Iterator<Map.Entry<K, V>> {

        private int index = 0 ;
        private Entry<K,V> lastItemReturned = null;
        private Iterator<Entry<K, V>> iter = null;

        @Override
        public boolean hasNext() {
        	// FILL HERE
        	return index < (numKeys - 1);
        	
        }

        @Override
        public Map.Entry<K, V> next() {
        	// FILL HERE
        	int currentIndex = 0;
        	Entry<K,V> currentNode;
        	LinkedList<Entry<K,V>> currentList;
        	if(this.hasNext()) {
        		while(currentIndex < index) {
        			
        		}
        	}
			return lastItemReturned;
        }

        @Override
        public void remove() {
        	// FILL HERE
        	throw new UnsupportedOperationException();
        }
    }

    ////////////// end SetIterator Class ////////////////////////////

    /**
     * Default constructor, sets the table to initial capacity size
     */
    public HashTableChain() {
        table = new LinkedList[CAPACITY] ;
    }

    // returns number of keys
    @Override
    public int size() {
        // FILL HERE
    	return numKeys; 
    }

    // returns boolean if table has no keys
    @Override
    public boolean isEmpty() {
    	// FILL HERE
    	return numKeys == 0;
    }

    // returns boolean if table has the searched for key
    @Override
    public boolean containsKey(Object key) {
    	
    	
    	Entry<K,V> currentNode;
    	LinkedList<Entry<K,V>> currentList;
    	int index = key.hashCode() % table.length;
    	
    	currentList = table[index];
    	if(currentList == null) {
    		return false;
    	}
    	
    	for(int i = 0; i < currentList.size(); i++) {
    		currentNode = currentList.get(i);
    		if(key.equals(currentNode.getKey())) {
    			return true;
    		}
    	}
    	
    	return false;
    }

    // returns boolean if table has the searched for value
    @Override
    public boolean containsValue(Object value) {

    	LinkedList<Entry<K, V>> currentList;
    	Entry<K,V> currentNode;
    	
    	
    	//For each LL in Array 
    	for(int i = 0; i < table.length; i++) {
    		//For each node in LL
    		currentList = table[i];
    		if(currentList != null) {
	    		for(int j = 0; j < currentList.size(); j++) {
	    			currentNode = currentList.get(j);
	    			if(currentNode.getValue() == value) {
	    				return true;
	    			}
	    		}
    		}
    	}
    	return false;
    	
    }

    // returns Value if table has the searched for key
    @Override
    public V get(Object key) {
    	// FILL HERE
    	int index = key.hashCode() % table.length;
    	 if (index < 0)
    	 index += table.length;
    	 if (table[index] == null)
    	 return null; // key is not in the table.
    	 // Search the list at table[index] to find the key.
    	 for (Entry<K, V> nextItem : table[index]) {
    	 if (nextItem.getKey().equals(key))
    	 return nextItem.getValue();
    	 }
    	 // assert: key is not in the table.
    	 return null;
    }

    // adds the key and value pair to the table using hashing
    @Override
    public V put(K key, V value) {
    	if(this.containsKey(key)) {
    		return null;
    	}
    	Entry<K,V> newNode = new Entry<K,V>(key,value);
    	int newHash = key.hashCode();
    	int newIndex = newHash % table.length; 
    	
    	//First Value at Index
    	if(table[newIndex] == null) {
    		//Make new LL
    		table[newIndex] = new LinkedList<Entry<K,V>>();
    	}
    	//Put new entry
    	table[newIndex].add(newNode);
    	numKeys++;
    	return value;
    	
    }


    /**
     * Resizes the table to be 2X +1 bigger than previous
     */
    private void rehash() {
    	// FILL HERE
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder() ;
        for (int i = 0 ; i < table.length ; i++ ) {
            if (table[i] != null) {
                for (Entry<K, V> nextItem : table[i]) {
                    sb.append(nextItem.toString() + " ") ;
                }
                sb.append(" ");
            }
        }
        return sb.toString() ;

    }

    // remove an entry at the key location
    // return removed value
    @Override
    public V remove(Object key) {
    	
    	LinkedList<Entry<K, V>> currentList;
    	V removedValue;
    	Entry<K,V> currentNode;
    	//For each LL in Array 
    	for(int i = 0; i < table.length; i++) {
    		//For each node in LL
    		currentList = table[i];
    		if(currentList != null) {
	    		for(int j = 0; j < currentList.size(); j++) {
	    			currentNode = currentList.get(j);
	    			if(currentNode.getKey().equals(key)) {
	    				removedValue = currentNode.getValue();
	    				currentList.remove(j);
	    				return removedValue; 
	    			}
	    		}
    		}
    	}
    	
    	//No value found
    	return null;
    }

    // throws UnsupportedOperationException
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException() ;
    }

    // empties the table
    @Override
    public void clear() {
    	// Fill HERE
    	table = new LinkedList[table.length];
    	numKeys = 0;
    }

    // returns a view of the keys in set view
    @Override
    public Set<K> keySet() {
    	// FILL HERE
    	
    	
    }

    // throws UnsupportedOperationException
    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException() ;
    }


    // returns a set view of the hash table
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
    	// FILL HERE
    	return new EntrySet();
		
    }

    @Override
    public boolean equals(Object o) {
    	// FILL HERE
    	Map<String, Integer> other = (Map<String, Integer>) o;
    	LinkedList<Entry<K,V>> currentList;
    	Entry<K,V> currentNode, otherNode;
    	V currentValue, otherValue;
    	K currentKey;
    	
    	for(int i = 0; i < table.length; i++) {
    		currentList = table[i];
    		if(currentList != null) {
	    		for(int j = 0; j < currentList.size(); j++) {
	    			 currentNode = currentList.get(j);
	    			 currentKey = currentNode.getKey();
	    			 currentValue = currentNode.getValue();
	    			 otherValue = (V) other.get(currentKey);
	    			 if(otherValue != currentValue) {
	    				 return false;
	    			 }
	    		}
    		}
    		
    	}
    	return true;
    }
    
    /*
     *  FIX METHOD
     *  vvvvvvvvvvv
     */
    @Override
    public int hashCode() {
    	
    	String number = "one";
    	return number.hashCode() + 1;
    }
}
