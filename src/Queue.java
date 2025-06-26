/**
 * A generic queue implementation using a linked list.
 * This class provides basic queue operations such as enqueue (add), and dequeue (poll).
 *
 * @param <E> the type of elements held in this queue
 */
public class Queue<E> {
    private LinkedList<E> list; // Underlying data structure for storing elements

    /**
     * Constructs an empty queue.
     */
    public Queue() {
        list = new LinkedList<>();
    }

    /**
     * Adds an element to the end of the queue.
     *
     * @param e the element to be added
     * @throws NullPointerException if the specified element is null
     */
    public void add(E e) {
        if (e == null) {
            throw new NullPointerException("Null elements are not allowed in this queue");
        }
        list.addLast(e);
    }

    /**
     * Removes and returns the element from the front of the queue, or returns null if the queue is empty.
     *
     * @return the element at the front of the queue, or null if the queue is empty
     */
    public E poll() {
        return list.isEmpty() ? null : list.removeFirst();
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return list.size();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }
}

