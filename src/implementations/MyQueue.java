package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * A doubly linked list-backed implementation of the {@link QueueADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class delegates all storage to an internal {@link MyDLL}, treating index
 * {@code 0} as the front of the queue and the last index as the back.
 * {@link #enqueue(String)} appends to the back; {@link #dequeue()} and
 * {@link #peek()} access the front. A queue follows a <strong>First-In,
 * First-Out (FIFO)</strong> ordering policy: the first element enqueued is the
 * first one returned.
 * </p>
 *
 * <p>
 * <strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only. A
 * fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyQueue<E>}), allowing any object type to be stored in the queue.
 * Generic programming is outside the scope of CPRG 304 and has therefore been
 * omitted. All element fields, parameters, and return types are fixed as
 * {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyQueue implements QueueADT {
	// Internal doubly linked list
	private MyDLL internalDLL;

	/**
	 * Creates an empty queue.
	 */
	public MyQueue() {
		internalDLL = new MyDLL();
	}

	/**
	 * Adds a String to the back of the queue.
	 *
	 * @param toAdd String being added
	 * @throws NullPointerException if toAdd is null
	 */
	@Override
	public void enqueue(String toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException();
		}

		internalDLL.add(toAdd);
	}

	/**
	 * Removes and returns the front element.
	 *
	 * @return the removed String
	 * @throws EmptyQueueException if the queue is empty
	 */
	@Override
	public String dequeue() throws EmptyQueueException {
		if (internalDLL.isEmpty()) {
			throw new EmptyQueueException();
		}

		return internalDLL.remove(0);
	}

	/**
	 * Returns the front element without removing it.
	 *
	 * @return the front String
	 * @throws EmptyQueueException if the queue is empty
	 */
	@Override
	public String peek() throws EmptyQueueException {
		if (internalDLL.isEmpty()) {
			throw new EmptyQueueException();
		}

		return internalDLL.get(0);
	}

	/**
	 * Removes all elements from the queue.
	 */
	@Override
	public void dequeueAll() {
		internalDLL.clear();
	}

	/**
	 * Checks whether the queue is empty.
	 *
	 * @return true if empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return internalDLL.isEmpty();
	}

	/**
	 * Checks whether the queue contains a String.
	 *
	 * @param toFind String being searched for
	 * @return true if found, false otherwise
	 * @throws NullPointerException if toFind is null
	 */
	@Override
	public boolean contains(String toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException();
		}

		return internalDLL.contains(toFind);
	}

	/**
	 * Searches for a String from the front of the queue.
	 *
	 * @param toFind String being searched for
	 * @return 1-based position, or -1 if not found
	 */
	@Override
	public int search(String toFind) {
		if (toFind == null) {
			throw new NullPointerException();
		}

		int position = internalDLL.indexOf(toFind);

		if (position == -1) {
			return -1;
		}

		return position + 1;
	}

	/**
	 * Creates an iterator for the queue.
	 *
	 * @return an iterator from front to back
	 */
	@Override
	public Iterator iterator() {
		return internalDLL.iterator();
	}

	/**
	 * Compares this queue with another queue.
	 *
	 * @param that queue being compared
	 * @return true if both queues contain the same elements in order
	 */
	@Override
	public boolean equals(QueueADT that) {
		if (that == null) {
			throw new NullPointerException();
		}

		if (this.size() != that.size()) {
			return false;
		}

		Iterator thisIterator = this.iterator();
		Iterator thatIterator = that.iterator();

		while (thisIterator.hasNext()) {
			String thisCurrent = thisIterator.next();
			String thatCurrent = thatIterator.next();

			if (!thisCurrent.equals(thatCurrent)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Copies the queue elements into an array.
	 *
	 * @param holder array to store the elements
	 * @return array containing queue elements
	 * @throws NullPointerException if holder is null
	 */
	@Override
	public String[] toArray(String[] holder) throws NullPointerException {
		if (holder == null) {
			throw new NullPointerException();
		}

		return internalDLL.toArray(holder);
	}

	/**
	 * Returns the number of elements in the queue.
	 *
	 * @return queue size
	 */
	@Override
	public int size() {
		return internalDLL.size();
	}
}