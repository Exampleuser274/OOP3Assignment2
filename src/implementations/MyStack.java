package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.StackADT;

/**
 * An array-backed implementation of the {@link StackADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class delegates all storage to an internal {@link MyArrayList},
 * treating the end of the list as the top of the stack. {@link #push(String)}
 * appends to the end; {@link #pop()} and {@link #peek()} access the last
 * element. A stack follows a <strong>Last-In, First-Out (LIFO)</strong>
 * ordering policy: the last element pushed is the first one returned.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyStack<E>}), allowing any object type to be stored in the stack.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyStack implements StackADT
{	
	//size of stack
	int size;
	//internal ArrayList structure (not java default)
	MyArrayList internalArrayList;
	/**
	 * Creates new Stack object and sets size to 0
	 */
	public MyStack() {
		internalArrayList = new MyArrayList();
		size = 0;
	}
	/**
	 * adds a String to the top of the stack
	 * 
	 * @param toAdd String being added to stack
	 * 
	 * @throws NullPointerException if toAdd is null
	 */
	@Override
	public void push(String toAdd) throws NullPointerException {
		if(toAdd==null) {
			throw new NullPointerException();
		}
		this.internalArrayList.add(toAdd);
		this.size++;
	}
	/**
	 * removes String from top of stack
	 * 
	 * @return The string that was removed
	 * @throws EmptyStackException if the stack is empty before removal
	 */
	@Override
	public String pop() throws EmptyStackException {
		if (this.size==0) {
			throw new EmptyStackException();
		}
		String removed = this.internalArrayList.remove(size-1);
		size--;
		return removed;
	}
	/**
	 * grabs the String from the top of stack
	 * 
	 * @return String from top of Stack
	 * @throws EmptyStackException if the stack is empty
	 */
	@Override
	public String peek() throws EmptyStackException {
		if (this.size==0) {
			throw new EmptyStackException();
		}
		String lastItem = this.internalArrayList.get(size-1);
		return lastItem;
	}
	/**
	 * Replaces stack with an empty stack and sets size to 0
	 */
	@Override
	public void clear() {
		this.internalArrayList.clear();
		this.size = 0;
		
	}
	/**
	 * checks if stack is empty
	 * 
	 * @return True if empty, false if not
	 */
	@Override
	public boolean isEmpty() {
		if(this.size==0) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * converts stack to an array
	 * 
	 * @param holder array to add stack items to
	 * 
	 * @return the converted array
	 * 
	 * @throws NullPointerException if holder is null
	 */
	@Override
	public String[] toArray(String[] holder) throws NullPointerException {
		if(holder==null) {
			throw new NullPointerException();
		}
		if (holder.length<this.size) {
			holder = new String[this.size];
		}
		holder = this.internalArrayList.toArray(holder);
		return holder;
	}
	/**
	 * checks if the stack contains a specific string
	 * 
	 * @param toFind the String being checked for
	 * 
	 * @return True if stack contains String, false if not
	 * 
	 * @throws NullPointerException if toFind is null
	 */
	@Override
	public boolean contains(String toFind) throws NullPointerException {
		if (toFind==null) {
			throw new NullPointerException();
		}
		
		return this.internalArrayList.contains(toFind);
	}
	/**
	 * searches the stack for a specific string
	 * 
	 * @param toFind the String being searched for
	 * 
	 * @return index of String if found (1 based, top to bottom) or -1 if not found
	 */
	@Override
	public int search(String toFind) {
		int max = this.size;
		int index = 0;
		int found = -1;
		Iterator iterator = this.iterator();
		// Search through the elements in the stack.
		while(iterator.hasNext()) {
			String current = iterator.next();
			if (current.equals(toFind)) {
				found = index;
			}
			index++;
		}
		 // Return the position relative to the top of the stack.
		if(found!=-1) {
			return max-found;
		}
		else return found; 

	}
	/**
	 * create new iterator for Stack
	 */
	@Override
	public Iterator iterator() {
		return new StackIterator();
	}
	/**
	 * iterator class for Stack
	 */
	private class StackIterator implements Iterator{
		private String[] array;
		private int index = 0;
		/**
		 * creates an iterator array from the Stack
		 */
		public StackIterator() {
			String[] tempArray = new String[internalArrayList.size()];
			this.array = internalArrayList.toArray(tempArray);
			
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
	/**
	 * compares the Stack with another supplied stack
	 * 
	 * @param that Stack to compare to
	 * 
	 * @return true if the stacks are equal, false if not
	 */
	@Override
	public boolean equals(StackADT that) {
		Iterator thisIterator = this.iterator();
		Iterator thatIterator = that.iterator();
		String thisCurrent;
		String thatCurrent;
		while(thisIterator.hasNext()||thatIterator.hasNext()) {
			if(!thisIterator.hasNext()||!thatIterator.hasNext()) {
				return false;
			}
			thisCurrent = thisIterator.next();
			thatCurrent = thatIterator.next();
			if(thisCurrent!=thatCurrent) {
				return false;
			}
		}
		return true;
	}
	/**
	 * retrieves the depth of the stack
	 * 
	 * @return the stack depth
	 */
	@Override
	public int size() {
		return this.size;
	}
  
}