package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * A doubly linked list-backed implementation of the {@link QueueADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class delegates all storage to an internal {@link MyDLL}, treating
 * index {@code 0} as the front of the queue and the last index as the back.
 * {@link #enqueue(String)} appends to the back; {@link #dequeue()} and
 * {@link #peek()} access the front. A queue follows a
 * <strong>First-In, First-Out (FIFO)</strong> ordering policy: the first
 * element enqueued is the first one returned.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyQueue<E>}), allowing any object type to be stored in the queue.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyQueue implements QueueADT
{

	@Override
	public void enqueue(String toAdd) throws NullPointerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String dequeue() throws EmptyQueueException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String peek() throws EmptyQueueException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dequeueAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(String toFind) throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int search(String toFind) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(QueueADT that) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] toArray(String[] holder) throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
    
}