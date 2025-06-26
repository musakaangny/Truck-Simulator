import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * A generic singly linked list implementation that supports basic operations such as adding, removing,
 * and accessing elements, as well as implementing the Iterable interface for iteration.
 *
 * @param <E> the type of elements held in this list
 */
public class LinkedList<E> implements Iterable<E> {

    // Node class representing an element in the linked list
    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<E> head; // The head (first node) of the linked list
    private Node<E> tail; // The tail (last node) of the linked list
    private int size; // The size of the linked list

    /**
     * Constructs an empty linked list.
     */
    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param data the element to add
     */
    public void add(E data) {
        addLast(data); // Delegate to addLast
    }

    /**
     * Adds an element to the end of the list (explicit method).
     *
     * @param data the element to add
     */
    public void addLast(E data) {
        Node<E> newNode = new Node<>(data);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * @return the removed element
     * @throws NoSuchElementException if the list is empty
     */
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        E data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return data;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the list.
     *
     * @return the number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * Removes a specific element from the list.
     *
     * @param o the element to remove
     * @return true if the element was removed, false otherwise
     */
    public boolean remove(Object o) {
        if (isEmpty()) return false;

        if (head.data.equals(o)) {
            removeFirst();
            return true;
        }

        Node<E> current = head;
        while (current.next != null && !current.next.data.equals(o)) {
            current = current.next;
        }

        if (current.next == null) return false;

        if (current.next == tail) {
            tail = current;
        }

        current.next = current.next.next;
        size--;
        return true;
    }

    /**
     * Clears all elements from the list.
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }

    /**
     * Returns an iterator over elements of the list.
     *
     * @return an iterator over the elements of this list
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    /**
     * Applies the given action to each element of the list.
     *
     * @param action the action to be performed for each element
     */
    public void forEach(Consumer<? super E> action) {
        Node<E> current = head;
        while (current != null) {
            action.accept(current.data);
            current = current.next;
        }
    }
}
