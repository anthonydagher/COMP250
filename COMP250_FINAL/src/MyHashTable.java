import java.util.ArrayList;

import java.util.Iterator;
import java.util.LinkedList;


public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS
    	//initialize fields
        this.numBuckets = initialCapacity;
        this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(this.numBuckets);
        this.numEntries = 0;
        
        for(int i=0; i<this.numBuckets; i++) {
        	LinkedList<HashPair<K,V>> toAdd = new LinkedList<HashPair<K,V>>();
        	this.buckets.add(toAdd);
        }
        //ADD YOUR CODE ABOVE THIS
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public boolean isEmpty() {
        return this.numEntries == 0;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int k = key.hashCode();
    	int val = Math.abs(k)%this.numBuckets;
        return val;
    }
    
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE
    	if(key.equals(null)) return null;
    		// find position of the key
    		int position = hashFunction(key);
    		LinkedList<HashPair<K, V>> bucket = this.buckets.get(position);
    		// iterate with enhanced for loop
    		for(HashPair<K,V> hash : bucket) {
    			K k = hash.getKey();
    			// check if the key already exists in hash table
    			if(k.equals(key)) {
    				// overwrite
    				if(hash.getValue() != null) {
    	    			V before = hash.getValue();
    					hash.setValue(value);
        				return before;
    				}
    				// if it doesn't, return null
    				else {
    					hash.setValue(value); 
    					return null;
    				}
    			}
    		}
    		// update size
    		this.numEntries = this.numEntries+1;
    		// add the hashpair to the position
    		buckets.get(position).add(new HashPair<K, V>(key, value));
    		// check that load factor should never be greater than the maximum load factor
    		// if so, gotta rehash
    		if(MAX_LOAD_FACTOR < (1+(double)this.numEntries)/(double)this.numBuckets) {
    			this.rehash();
    		}
    		// if nothing is the case
    		return null;
        //  ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */
    
    public V get(K key) {
        //ADD YOUR CODE BELOW HERE
    	if(key.equals(null)) return null;
        // find position of the key
       	int position = hashFunction(key);
        LinkedList<HashPair<K, V>> bucket = this.buckets.get(position);
        // if it's not an empty list, search it
        if(!bucket.isEmpty()) {
        	// iterate with enhanced for loop
        	for(HashPair<K,V> hash : bucket) {
        		K k = hash.getKey();
        		if(k.equals(key)) {
        			// return the value if correct key
        			V value = hash.getValue();
        			return value;
        		}	
        	}
        }
        // if nothing is the case
    	return null;
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
    	if(key.equals(null)) return null;
        // find position of the key
    		int position = hashFunction(key);
    		LinkedList<HashPair<K, V>> bucket = this.buckets.get(position);
    		// if it's not an empty list, search it
    		if(!bucket.isEmpty()) {
    			// iterate with enhanced for loop
    			for(HashPair<K,V> hash : bucket) {
    				K k = hash.getKey();
    				if(k.equals(key)) {
    					// remove key and return value if correct key
    					V value = hash.getValue();
    					bucket.remove(hash);
    					//update  size
    					this.numEntries = this.numEntries-1;
    					return value;
    				}
    			}
    		}
    	// if nothing is the case
    	return null;
        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /** 
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
        //ADD YOUR CODE BELOW HERE
    	// double the size of the hashtable and change it
    	ArrayList<LinkedList<HashPair<K,V>>> older = this.buckets;
    	int newSize = this.numBuckets*2;
    	this.numBuckets = newSize;
    	ArrayList<LinkedList<HashPair<K,V>>> newBucket = new ArrayList<LinkedList<HashPair<K,V>>>(newSize);
    	// update the bucket to the new size
    	this.buckets = newBucket;
    	// add list to make it two times its original size
    	for(int i=0; i<newSize; i++) {
    		LinkedList<HashPair<K,V>> l = new LinkedList<HashPair<K,V>>();
    		this.buckets.add(i,l);
    	}
    	// add the corresponding pairs from the list of the previous bucket to the new one
    	// iterate with enhanced for loop
    	for(LinkedList<HashPair<K,V>> list : older) {
    		if(!list.isEmpty()) {
    			// now copy over
    	    	// iterate with enhanced for loop
    			for(HashPair<K,V> hash : list) {
    				// add to corresponding position
    				this.numEntries = this.numEntries+1;
    				this.buckets.get(hashFunction(hash.getKey())).add(hash);
    			}
    		}
    	}
        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    
    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE
        // Must return an array list, so we create one to store the keys
    	ArrayList<K> keys = new ArrayList<K>();
    	ArrayList<LinkedList<HashPair<K, V>>> bucket = this.buckets;
    	for(LinkedList<HashPair<K, V>> hash : bucket) {
    		// if we're dealing with an empty bucketlist, return the array
    		if(hash.equals(null)) return keys;
    			for(HashPair<K, V> hPair : hash) {
    				// add the key to the array
    				K key = hPair.getKey();
    				keys.add(key);
    			}
    		}
    	// return the array of values
    	return keys;
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
    	//ADD YOUR CODE BELOW HERE
    	// create table with size of all entries
    	MyHashTable<V, V> table = new MyHashTable<V,V>(this.numBuckets);
    	ArrayList<LinkedList<HashPair<K, V>>> bucket = this.buckets;
    	// enhanced for loop to iterate through
    	for(LinkedList<HashPair<K, V>> hash : bucket) {
    		// if we're dealing with an empty bucketlist, return the empty array
    		if(hash.equals(null)) return table.keys();
    			for(HashPair<K, V> hPair : hash) {
    				// add the key to the array
    				V value = hPair.getValue();
    				table.put(value, value);
    			}
    		}
    	// return the array of values
    	return table.keys();
        //ADD YOUR CODE ABOVE HERE
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
        //ADD CODE BELOW HERE
    	// we should start by storing the keys into an array list for sorting
    	// this will be the key output (just like in slow sort)
    	ArrayList<K> output = new ArrayList<K>();
    	// enhanced for loop iteration with provided input
    	for(K hash: results.keys()) {
    		output.add(hash);
    	}
    	// parameters for quick sort
    	int low = 0;
    	int high = output.size()-1;
    	// now, sort using basic quick sort method
    	sort(results, output, low, high);
    	
    	return output;
        //ADD CODE ABOVE HERE
    }
    // basic quick sort method taught in class
    public static <K, V extends Comparable<V>> void sort(MyHashTable<K, V> array, ArrayList<K> output, int lowest, int highest) {
    	if(lowest>=highest) return;
    	else {
    		
    		int next = placeDivide(array, output, lowest, highest);
    		sort(array, output, lowest, next-1);
    		sort(array, output, next+1, highest);
    	}
    }
    // required placeDivide method taught in class
    private static <K, V extends Comparable<V>> int placeDivide(MyHashTable<K, V>array, ArrayList<K> output, int lowest, int highest) {
    	
    	int wall = lowest-1;
    	for(int i = lowest; i <= highest-1; i++) {
    		// returns positive number if the object that we want to compare is greater than the specified one
    		if(array.get(output.get(i)).compareTo(array.get(output.get(highest)))>0) {
    			wall = wall+1;
    			// perform a typical info swap 
    			K temp = output.get(wall);
    			K next = output.get(i);
    			// set the info to their new locations
    			// " MOVE ELEMENT BEHIND WALL"
    			output.set(i, temp);
    			output.set(wall, next);
    		}
    	}
    	int update = wall+1;
    	// perform a typical info swap 
		K temp = output.get(update);
		K next = output.get(highest);
		// set the info to their new locations
		// " MOVE PIVOT NEXT TO WALL"
		output.set(highest, temp);
		output.set(update, next);
    	// return index for the sort method
		return update;
	}

	@Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE
    	LinkedList<HashPair<K, V>> list;
        //ADD YOUR CODE ABOVE HERE
    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
        	list = new LinkedList<HashPair<K, V>>();
        	//LinkedList<HashPair<K, V>> newList
        	for(LinkedList<HashPair<K, V>> newList: buckets) {
        		if(!newList.isEmpty()) {
        			//HashPair<K, V> newHash;
        			for(HashPair<K, V> newHash: newList) {
        				list.add(newHash); // adds the hash pair to the list
        			}
        		}else continue;
        	}
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
        	boolean has = list.isEmpty(); // checks if empty
        	return !has; // returns true if not empty (so it has a next)
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	// this returns the first in the list, which is in fact what is looked for
        	// basically, create a variable "next" and set it to the first element that
        	// is being removed, this doesn't actually remove the real element (in the real list)
        	// we're just setting this new variable equal to the first element (pretend to remove)
        	HashPair<K, V> next = list.removeFirst();
        	return next; 
            //ADD YOUR CODE ABOVE HERE
        }   
    }
}
