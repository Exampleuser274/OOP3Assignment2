package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;

/**
 * A dynamic array-based implementation of the {@link ListADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class stores {@link String} elements in an internally managed array
 * that grows automatically when capacity is reached. The growth amount is
 * determined by a configurable growth rate expressed as a decimal percentage
 * of the current array length (e.g., {@code 0.25} means 25% growth).
 * All index parameters are zero-based unless otherwise noted.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyArrayList<E>}), allowing any object type to be stored in the list.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyArrayList implements ListADT
{	
	//integer for size of ArrayList
	private int size;
	//Array that the MyArrayList methods modify
	private String[] internalArray;
	
	/**
	 * creates an empty arrayList *not the java default implementation* object and sets the size to 0
	 */
	public MyArrayList() {
		this.internalArray = new String[0];
		this.size = 0;
	}
	/**
	 * returns the size of the list
	 * 
	 * @return integer representing the size of the ArrayList
	 */
	@Override
	public int size() {
		return this.size;
	}
	/**
	 * replaces the current list with an empty one and sets the size to 0
	 */
	@Override
	public void clear() {
		this.internalArray = new String[0];
		this.size = 0;
		
	}
	/**
	 * adds a new value to the list at a supplied index
	 * 
	 * @param index the position of the element to be added (0 based)
	 * @param toAdd the String to be added
	 * 
	 * @return true if addition is successful
	 * 
	 * @throws NullPointerException if toAdd is null
	 * @throws IndexOutOfBoundsException if the index is smaller than 0 or larger than the size of the list
	 */
	@Override
	public boolean add(int index, String toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if(toAdd==null) {
			throw new NullPointerException();
		}
		if(index<0||this.size<index) {
			throw new IndexOutOfBoundsException();
		}
		String[] newArray = new String[this.size+1];
		
		System.arraycopy(this.internalArray, 0, newArray,0 , index);
		newArray[index] = toAdd;
		System.arraycopy(this.internalArray, index, newArray, index+1, this.size-index);
		this.internalArray = newArray;
		
		size++;
		return false;
	}
	/**
	 * adds a new value to the end of the array
	 * 
	 * @param toAdd the String being added to the list
	 * 
	 * @return True if successful
	 * @throws NullPointerException if toAdd is null
	 */
	@Override
	public boolean add(String toAdd) throws NullPointerException {
		if(toAdd==null) {
			throw new NullPointerException();
		}
		this.size++;
		String[] arrayCopy = Arrays.copyOf(this.internalArray, this.size);
		arrayCopy[this.size-1] = toAdd;
		this.internalArray = arrayCopy;
		return true;
	}
	/**
	 * appends another arraylist to the current list
	 * 
	 * @param toAdd the arraylist being added
	 * 
	 * @return True if successful
	 * 
	 * @throws NullPointerException if toAdd is null
	 */
	@Override
	public boolean addAll(ListADT toAdd) throws NullPointerException {
		if(toAdd==null) {
			throw new NullPointerException();
		}

		int newSize = toAdd.size() + this.size;
		String[] tempArray = new String[toAdd.size()];
		tempArray = toAdd.toArray(tempArray);
		String[] newArray = new String[newSize];
		System.arraycopy(this.internalArray, 0, newArray, 0, this.size);
		System.arraycopy(tempArray, 0, newArray, this.size, toAdd.size());
		this.size = newSize;
		this.internalArray = newArray;
		return true;
	}
	/**
	 * retrieves the String at the supplied index
	 * 
	 * @param index of the String being retrieved
	 * 
	 * @return String at the supplied index
	 * 
	 * @throws IndexOutOfBoundsException if index is smaller than 0 or larger than the size of the array
	 * 
	 */
	@Override
	public String get(int index) throws IndexOutOfBoundsException {
		if(index<0||this.size<index) {
			throw new IndexOutOfBoundsException();
		}
		return this.internalArray[index];
	}
	/**
	 * removes an item from the array by it's index
	 * 
	 * @param index index of String to be removed
	 * 
	 * @return the removed String
	 * 
	 * @throws IndexOutOfBoundsException if index is smaller than 0 or larger than the size of the array
	 */
	@Override
	public String remove(int index) throws IndexOutOfBoundsException {
		if (index<0||this.size<index) {
			throw new IndexOutOfBoundsException();
		}
		String removed = this.internalArray[index];
		String[] newArray = new String [this.size-1];
		System.arraycopy(this.internalArray, 0, newArray, 0, index);
		System.arraycopy(this.internalArray, index + 1, newArray, index, this.size - index - 1);
		this.internalArray = newArray;
		this.size--;
		return removed;
	}
	/**
	 * removes an item from the array by it's value
	 * 
	 * @param toRemove the value of the String to be removed
	 * 
	 * @return either the removed String or null if the String is not in the array
	 * 
	 * @throws NullPointerException if toRemove is null
	 */
	@Override
	public String remove(String toRemove) throws NullPointerException {
		if (toRemove==null) {
			throw new NullPointerException();
		}
		if(this.isEmpty()) {
			return null;
		}
		int index = 0;
		String current;
		String removed = null;
		boolean found = false;
		Iterator newIterator = iterator();
		while (newIterator.hasNext()) {
			current = newIterator.next();
			if (toRemove==current) {
				found = true;
				break;
			}
			index++;
		}
		if (found==true) {
			removed = this.internalArray[index];
			String[] newArray = new String [this.size-1];
			System.arraycopy(this.internalArray, 0, newArray, 0, index);
			System.arraycopy(this.internalArray, index + 1, newArray, index, this.size - index - 1);
			this.internalArray = newArray;
			this.size--;	
		}
		return removed;
	}
	/**
	 * overwrites a string at supplied index with a new value
	 * 
	 * @param index index of String to be replaced
	 * @param toChange new value to be added
	 * 
	 * @return the String that was removed
	 * 
	 * @throws IndexOutOfBoundsException if supplied index is beyond the range of the list or is smaller than 0
	 */
	@Override
	public String set(int index, String toChange) throws NullPointerException, IndexOutOfBoundsException {
		if(index>=this.size||index<0) {
			throw new IndexOutOfBoundsException();
		}
		if(toChange==null) {
			throw new NullPointerException();
		}
		String oldString = this.internalArray[index];
		this.internalArray[index] = toChange;
		return oldString;
	}
	/**
	 * checks if the arraylist is empty or not
	 * 
	 * @return True if empty, false if not
	 */
	@Override
	public boolean isEmpty() {
		if (this.size == 0) {
			return true;
		}else {return false;}
		
	}
	/**
	 * check if arraylist contains a specified value
	 * 
	 * @param toFind String being searched for
	 * 
	 * @return True if found, false if not
	 * 
	 * @throws NullPointerException if toFind is null
	 */
	@Override
	public boolean contains(String toFind) throws NullPointerException {
		if(toFind==null) {
			throw new NullPointerException();
		}
		String current;
		Iterator newIterator = iterator();
		while (newIterator.hasNext()) {
			current = newIterator.next();
			if (toFind==current) {
				return true;
			}
		}
		return false;
	}
	/**
	 * retrieves the index of a specified String
	 * 
	 * @param String being searched for
	 * 
	 * @return index of string if found, -1 if not found
	 * 
	 * @throws NullPointerException if tofind is null
	 */
	@Override
	public int indexOf(String toFind) throws NullPointerException {
		if(toFind==null) {
			throw new NullPointerException();
		}
		int index = 0;
		String current;
		Iterator newIterator = iterator();
		while (newIterator.hasNext()) {
			current = newIterator.next();
			if (toFind==current) {
				return index;
			}
			index++;
		}
		return -1;
	}
	/**
	 * converts the ArrayList object to an array
	 * 
	 * @param toHold the array values are fed into
	 * 
	 * @return the filled array
	 * 
	 * @throws NullPointerException if toHold is null
	 */
	@Override
	public String[] toArray(String[] toHold) throws NullPointerException {
		if (toHold==null) {
			throw new NullPointerException();
		}
		if (toHold.length<this.size) {
			toHold = new String[this.size];
		}
		for(int i=0;i<this.size;i++) {
			toHold[i] = this.internalArray[i];
		}
		
		return toHold;
	}
	/**
	 * creates new iterator for the ArrayList
	 */
	@Override
	public Iterator iterator() {
		return new ArrayListIterator();
	}
	/**
	 * Iterator Class for the ArrayList object
	 */
	private class ArrayListIterator implements Iterator{
		private String[] array;
		private int index = 0;
		/**
		 * creates an iterator array from the arraylist
		 */
		public ArrayListIterator() {
			this.array = internalArray;
			
		}
		/**
		 * checks if there are more elements to iterate through
		 */
		@Override
		public boolean hasNext() {
			return index < array.length;
		}
		/**
		 * moves the pointer to the next position
		 * 
		 * @return the current element
		 * 
		 * @throws NoSuchElementException if there is no element to iterate to
		 */
		@Override
		public String next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			String position = this.array[index];
			index++;
			return position;
		}
		
	}
   
}