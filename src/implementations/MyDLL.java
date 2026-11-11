package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;

/**
 * A doubly linked list (DLL) implementation of the {@link ListADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class stores {@link String} elements in a chain of {@link MyDLLNode}
 * objects, each of which holds a reference to both its predecessor ({@code pred})
 * and its successor ({@code succ}). The list maintains explicit {@code head}
 * and {@code tail} references for O(1) access to both ends of the chain.
 * Insertions and removals at arbitrary positions require O(n) traversal.
 * All index parameters are zero-based unless otherwise noted.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyDLL<E>}), allowing any object type to be stored in the list.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyDLL implements ListADT
{

	// First node in the list
    private MyDLLNode head;

    // Last node in the list
    private MyDLLNode tail;

    // Number of elements in the list
    private int size;

    /**
     * Creates an empty doubly linked list.
     * Both head and tail are set to null, and the size starts at zero.
     */
    public MyDLL() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
	
    /**
     * Returns the current number of elements in the list.
     *
     * @return The number of elements in the list.
     */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Removes all elements from the list.
	 * Resets head and tail to null and sets the size to zero.
	 */
	@Override
	public void clear() {
		// Remove references to the first and last nodes
	    this.head = null;
	    this.tail = null;

	    // Reset the number of elements
	    this.size = 0;
	}

	/**
	 * Adds a new element at the specified index in the list.
	 * Handles inserting at the beginning, middle, or end of the list.
	 *
	 * @param index The position where the new element will be added.
	 * @param toAdd The String element to add to the list.
	 * @return True if the element was successfully added.
	 * @throws NullPointerException If the element being added is null.
	 * @throws IndexOutOfBoundsException If the index is outside the valid range.
	 */
	@Override
	public boolean add(int index, String toAdd) throws NullPointerException, IndexOutOfBoundsException {
		
		// These exceptions check for invalid values, which are defined by the ListADT interface we implement
		
		// First, check if the element is null
		if (toAdd == null) {
	        throw new NullPointerException();
	    }
		
		// And check to make sure the index is within the size boundary of the list
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		
		// Create a new node to insert into the list
		MyDLLNode newNode = new MyDLLNode(toAdd, null, null);
		
		if (index == 0) {

			// If the list is empty, the new node becomes both head and tail
			if (this.isEmpty()) {
				this.head = newNode;
			    this.tail = newNode;
			}
			else {
				// Connect the new node to the current head
			    newNode.setSucc(this.head);

			    // Connect the current head back to the new node
			    this.head.setPred(newNode);

			    // Update head to the new node
			     this.head = newNode;
			}
		}
		
		// This else if handles adding something to the end of the list
		// While also making sure the nodes get properly connected 
		else if (index == size) {

		    // Connect the current tail to the new node
		    this.tail.setSucc(newNode);

		    // Connect the new node back to the current tail
		    newNode.setPred(this.tail);

		    // Update tail to the new last node
		    this.tail = newNode;
		}
		
		// This last else will handle anything thats not an empty list
		// Or adding to the end of the list. 
		else {
			
		    // Start at the first node
		    MyDLLNode current = this.head;

		    // Move through the list until we reach the requested index
		    for (int i = 0; i < index; i++) {
		        current = current.getSucc();
		    }

		    // Connect the new node to the node before current
		    current.getPred().setSucc(newNode);
		    newNode.setPred(current.getPred());

		    // Connect the new node to current
		    current.setPred(newNode);
		    newNode.setSucc(current);
		}
		
		// Make sure to increase the size as we have now added a new element into the list
		this.size++;
		
		// we return true because an element was successfully added
		return true;
			
	}

	/**
	 * Adds a new element to the end of the list.
	 * Updates the tail reference and increases the list size.
	 *
	 * @param toAdd The String element to add to the list.
	 * @return True if the element was successfully added.
	 * @throws NullPointerException If the element being added is null.
	 */
	@Override
	public boolean add(String toAdd) throws NullPointerException {

	    // First, check if the element is null
	    if (toAdd == null) {
	        throw new NullPointerException();
	    }

	    // Create a new node with no previous or next node
	    MyDLLNode newNode = new MyDLLNode(toAdd, null, null);

	    // If the list is empty, the new node is both head and tail
	    if (this.isEmpty()) {
	        this.head = newNode;
	        this.tail = newNode;
	    } 
	    else {
	        // Connect the current tail to the new node
	        this.tail.setSucc(newNode);

	        // Connect the new node back to the current tail
	        newNode.setPred(this.tail);

	        // Update tail to the new last node
	        this.tail = newNode;
	    }

	    // We also have to increase the size of the list because something was added to it
	    this.size++;

	    // Then return true because of the successful add
	    return true;
	}


	/**
	 * Adds all elements from another list to the end of this list.
	 * Uses an iterator to go through the supplied list and add
	 * each element in its original order.
	 *
	 * @param toAdd The list containing the elements to add.
	 * @return True when the elements have been successfully added.
	 * @throws NullPointerException If the supplied list is null.
	 */
	@Override
	public boolean addAll(ListADT toAdd) throws NullPointerException {
	
	    // Check if the supplied list is null
	    if (toAdd == null) {
	        throw new NullPointerException();
	    }
	
	    // Create an iterator for the supplied list
	    Iterator current = toAdd.iterator();
	
	    // Loop through the supplied list while elements remain
	    while (current.hasNext()) {
	
	        // Get the next element and add it to the end of our list
	        this.add(current.next());
	    }
	
	    // Return true after adding the elements
	    return true;
	}

	/**
	 * Retrieves the element stored at the specified index.
	 * Traverses the list from the head until the index is reached.
	 *
	 * @param index The position of the element to retrieve.
	 * @return The String element stored at the specified index.
	 * @throws IndexOutOfBoundsException If the index is outside the list.
	 */
	@Override
	public String get(int index) throws IndexOutOfBoundsException {
	
	    // Check if the index is within the list
		// We have to include the last element in the list, whoch would be size - 1. so >= size is out of bounds
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException();
	    }
	
	    // Start at the first node
	    MyDLLNode current = this.head;
	
	    // loop through the list until we reach the requested index
	    for (int i = 0; i < index; i++) {
	        current = current.getSucc();
	    }
	
	    // Return the element stored in the node
	    return current.getElement();
	}

	/**
	 * Removes the element at the specified index.
	 * Handles removing the only node, head, tail, or a middle node.
	 * Updates the node references and decreases the list size.
	 *
	 * @param index The position of the element to remove.
	 * @return The String element that was removed from the list.
	 * @throws IndexOutOfBoundsException If the index is outside the list.
	 */
	@Override
	public String remove(int index) throws IndexOutOfBoundsException {
		
		// Check to make sure the index is in the list
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		// Start at the first node
	    MyDLLNode current = this.head;

	    // Move through the list until we reach the requested index
	    for (int i = 0; i < index; i++) {
	        current = current.getSucc();
	    }

	    // Save the element before removing its node
	    // This is so we can return the removed value at the end 
	    String removedElement = current.getElement();
	    
	    // This will remove the only node in the list. if the list just happens to have one element in it
	    if (size == 1) {
	    	
	    	// The only node is both head and tail
	        // Set both to null to make the list empty
	        this.head = null;
	        this.tail = null;
	    }
	    
	    // This will be for removing just the head of the list
	    else if (index == 0) {
	    	
	    	// Move head to the next node in the list
	        this.head = this.head.getSucc();

	        // Remove the new head's reference to the old head
	        this.head.setPred(null);
	    }
	    
	    // This removes just the tail of the list
	    else if (index == size - 1) {
	    	
	    	// Move tail to the previous node in the list
	        this.tail = this.tail.getPred();

	        // Remove the new tail's reference to the old tail
	        this.tail.setSucc(null);
	    }
	    
	    // And this will handle everything in between 
		else {
		
		    // Connect the previous node to the next node
		    current.getPred().setSucc(current.getSucc());
		
		    // Connect the next node back to the previous node
		    current.getSucc().setPred(current.getPred());
		}

	    
	    // we removed something so we have to shrink the size now
	    this.size--;
	    
	    // then we return the value of the element we just removed. 
	    return removedElement;
	}

	/**
	 * Removes the first occurrence of the specified element from the list
	 * Loops through the list until the a matching element is found
	 * 
	 * @param toRemove The string element to remove from the list
	 * @return The Removed Element. or null if that element isnt found
	 * @throws NullPointerException if the element being searched is null
	 */
	@Override
	public String remove(String toRemove) throws NullPointerException {
		
		// make sure the value we are trying to remove isnt null first
		if (toRemove == null) {
			throw new NullPointerException();
		}
		
		// Start at the first node
		MyDLLNode current = this.head;
		
		// Loop through the list to find the element 
		for (int i = 0; i < size; i++) {
			
			// Check to see if the current element matches the one we want to remove.
			if (current.getElement().equals(toRemove)) {
				
				// We can use our existing method to remove the element 
				// The head, tail, and middle section removal logic is already in the method from earlier as well
				return this.remove(i);
			}
			//Moves to the next node 
			current = current.getSucc();
		}
		
		// REturn null if the element is not found
		return null;
	}

	/**
	 * Replaces the element at the specified index with a new element.
	 * The list size remains unchanged.
	 *
	 * @param index The position of the element to replace.
	 * @param toChange The new String element to store.
	 * @return The original String element before it was replaced.
	 * @throws NullPointerException If the new element is null.
	 * @throws IndexOutOfBoundsException If the index is outside the list.
	 */
	@Override
	public String set(int index, String toChange) throws NullPointerException, IndexOutOfBoundsException {
	
	    // Check if the new element is null
	    if (toChange == null) {
	        throw new NullPointerException();
	    }
	
	    // Check if the index is within the list
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException();
	    }
	
	    // Start at the first node
	    MyDLLNode current = this.head;
	
	    // loop through the list until we reach the requested index
	    for (int i = 0; i < index; i++) {
	        current = current.getSucc();
	    }
	
	    // Save the old element before replacing it
	    String oldElement = current.getElement();
	
	    // Replace the element with the new one
	    current.setElement(toChange);
	
	    // Return the element that was replaced
	    return oldElement;
	}

	/**
	 * Checks whether the list contains any elements.
	 *
	 * @return True if the list is empty, or false if it contains elements.
	 */
	@Override
	public boolean isEmpty() {
	    return this.size == 0;
	}

	/**
	 * Checks weather the specified element exists in the list
	 * Loops through the list until a matching element is found
	 * 
	 * @param toFind The String element to search for
	 * @return True if the element is found, or false if it isnt
	 *  @throws NullPointerException If the element being searched for is null.
	 */
	@Override
	public boolean contains(String toFind) throws NullPointerException {
		
		// Make sure the String isnt null first.
		if (toFind == null) {
			throw new NullPointerException();
		}
		
		// Start at the first node
		MyDLLNode current = this.head;
				
		// Loop through the list to find the element 
		for (int i = 0; i < size; i++) {
					
			// Check to see if the current element matches what we are looking for.
			if (current.getElement().equals(toFind)) {
				return true;
			}
			
			// moves to the next node in the list
			current = current.getSucc();
				
		}
		
		// Return false if the element cant be found
		return false;
	}

	/**
	 * Searched the list for the first occurrence of a specified element
	 * Loops through the list until a matching element is found
	 * 
	 * @param toFind The string element to search for
	 * @return The index of the first matching element, or -1 if not found
	 * @throws NullPointerException if the element being searched for is null
	 */
	@Override
	public int indexOf(String toFind) throws NullPointerException {
		
		// make sure the String isnt null first
		if (toFind == null) {
			throw new NullPointerException();
		}
		
		// Start at the first node
		MyDLLNode current = this.head;
		
		// Loop through the list to find the element 
		for (int i = 0; i < size; i++) {
			// Check to see if the current element matches what we are looking for.
			if (current.getElement().equals(toFind)) {
				// return the index of the element if we find it
				return i;
			}
			
			// Move to the next node
			current = current.getSucc();
		}
		
		// Return -1 if the element isnt found
		return -1;
	}

	/**
	 * Copies the elements from the list into a string array
	 * Loops through the list from head to tail, keeping the elements in order
	 * creates a new array if the provided array is too small
	 * 
	 * @param toHold The String array that will hold the lists elements
	 * @return The array containing the elements from the list.
	 * @throws NullPointerException if the provided array is null
	 */
	@Override
	public String[] toArray(String[] toHold) throws NullPointerException {
		
		// Make sure the provided array isnt null first
		if (toHold == null) {
			throw new NullPointerException();
		}
		
		// if the provided array is too small, we create a new array to store everything
		if (toHold.length < size) {
			toHold = new String[size];
		}
		
		// Start at the first node
		MyDLLNode current = this.head;
		
		// Loop through the list and copy each element
		for (int i = 0; i < size; i++) {
			
			// Store the current nodes element in the array
			toHold[i] = current.getElement();
			
			// Then move to the next node
			current = current.getSucc();
			// We dont leave the loop till all the elements have been copied
		}
		
		// Return the new array containing all the elements we looped through
		return toHold;
	}

	/**
	 * Creates an iterator to go through the list elements in order.
	 * Each iterator uses its own snapshot of the list.
	 *
	 * @return A new iterator for the current list.
	 */
	@Override
	public Iterator iterator() {
		return new DLLIterator();
	}
	
	/**
	 * An inner class that allows us to iterate through the list.
	 * Stores a copy of the elements so changes to the original list
	 * do not affect the iterator.
	 */
	private class DLLIterator implements Iterator {
	
	    // Array that stores a copy of the list elements
	    private String[] elements;
	
	    // Keeps track of the next element to return
	    private int position;
	
	    /**
	     * Creates an iterator by copying the current list elements
	     * into a String array.
	     */
	    public DLLIterator() {
	
	        // Create an array based on the current list size
	        elements = new String[size];
	
	        // Start at the first node
	        MyDLLNode current = head;
	
	        // Loop through the list and copy each element into the array
	        for (int i = 0; i < size; i++) {
	
	            // Store the current node's element in the array
	            elements[i] = current.getElement();
	
	            // Move to the next node
	            current = current.getSucc();
	        }
	
	        // Start the iterator at the first position
	        position = 0;
	    }
	
	    /**
	     * Checks whether the iterator has more elements.
	     *
	     * @return True if another element exists, or false otherwise.
	     */
	    @Override
	    public boolean hasNext() {
	
	        // Check if the current position is inside the array
	        return position < elements.length;
	    }
	
	    /**
	     * Returns the next element from the iterator.
	     *
	     * @return The next String element.
	     * @throws NoSuchElementException If there are no more elements.
	     */
	    @Override
	    public String next() throws NoSuchElementException {
	
	        // Check if we have reached the end of the array
	        if (!hasNext()) {
	            throw new NoSuchElementException();
	        }
	
	        // Save the element at the current position
	        String element = elements[position];
	
	        // Move to the next position
	        position++;
	
	        // Return the element
	        return element;
	    }
	}

}