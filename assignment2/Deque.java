package assignment2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue (deque) implementation using a doubly linked list.
 * @param <Item> the generic type of item in this deque
 */
public class Deque<Item> implements Iterable<Item> {

    // A private inner class to represent a node in the linked list
    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first; // A reference to the first node in the deque
    private Node last;  // A reference to the last node in the deque
    private int size;   // The number of items in the deque

    /**
     * [cite_start]Constructs an empty deque. [cite: 13, 117]
     */
    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    /**
     * [cite_start]Checks if the deque is empty. [cite: 14, 118]
     * @return true if the deque is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * [cite_start]Returns the number of items in the deque. [cite: 15, 119]
     * @return the number of items
     */
    public int size() {
        return this.size + 1;
    }

    /**
     * [cite_start]Adds an item to the front of the deque. [cite: 16, 120]
     * @param item the item to add
     */
    public void addFirst(Item item) {
        // We will implement this
        // 1. Check for invalid input.
        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null item.");
        }

        // 2. Create the new node and store the item.
        Node newNode = new Node();
        newNode.item = item;

        // 3. Check if the deque is empty.
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            // Wire the new node into the front of the list.
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }

        size++;
    }

    /**
     * [cite_start]Adds an item to the back of the deque. [cite: 17, 121]
     * @param item the item to add
     */
    public void addLast(Item item) {
        // We will implement this
        // 1. Check for invalid input.
        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null item.");
        }

        // 2. Create the new node.
        Node newNode = new Node();
        newNode.item = item;

        // 3. Check if the deque is empty.
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            // Wire the new node into the back of the list.
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
    }

    /**
     * [cite_start]Removes and returns the item from the front of the deque. [cite: 18, 122]
     * @return the item removed
     */
    public Item removeFirst() {
        // 1. Check if the deque is empty.
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }

        // 2. Save the item to return later.
        Item item = first.item;

        // 3. Decrement the size.
        size--;

        // 4. Handle the list pointers.
        if (isEmpty()) {
            // The list is now empty.
            first = null;
            last = null;
        } else {
            // Unlink the old first node.
            first = first.next;
            first.prev = null;
        }

        // 5. Return the removed item.
        return item;
    }

    /**
     * [cite_start]Removes and returns the item from the back of the deque. [cite: 19, 123]
     * @return the item removed
     */
    public Item removeLast() {
        // 1. Check if the deque is empty.
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }

        // 2. Save the item to return.
        Item item = last.item;

        // 3. Decrement the size.
        size--;

        // 4. Handle the list pointers.
        if (isEmpty()) {
            // The list is now empty.
            first = null;
            last = null;
        } else {
            // Unlink the old last node.
            last = last.prev;
            last.next = null;
        }

        // 5. Return the removed item.
        return item;
    }

    /**
     * [cite_start]Returns an iterator over items in order from front to back. [cite: 20, 124]
     * @return an iterator
     */
    public Iterator<Item> iterator() {
        // We will implement this later
        return new DequeIterator();
    }

    // A private iterator class
    private class DequeIterator implements Iterator<Item> {
        // This node tracks the iterator's current position.
        private Node current = first;

        public boolean hasNext() {
            // There is a next item if our current position is not null.
            return current != null;
        }

        public Item next() {
            // First, check if there is a next item.
            if (!hasNext()) {
                throw new NoSuchElementException("No more items in iteration.");
            }
            // Save the item from the current position.
            Item item = current.item;
            // Move to the next node for the next call.
            current = current.next;
            // Return the saved item.
            return item;
        }

        public void remove() {
            // This is an optional operation, which we will not implement.
            throw new UnsupportedOperationException();
        }
    }

    /**
     * [cite_start]Unit testing. [cite: 28, 132]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Create a new Deque that holds Strings
        Deque<String> deque = new Deque<>();
        System.out.println("Is the deque empty? " + deque.isEmpty()); // Should be true

        // Test addFirst and addLast
        deque.addFirst("B"); // Deque: B
        deque.addLast("C");  // Deque: B, C
        deque.addFirst("A"); // Deque: A, B, C

        System.out.println("Current size: " + deque.size()); // Should be 3

        // Test the iterator
        System.out.print("Contents of deque: ");
        for (String s : deque) {
            System.out.print(s + " "); // Should print: A B C
        }
        System.out.println();

        // Test removeLast and removeFirst
        String removedLast = deque.removeLast(); // Removes "C"
        System.out.println("Removed last item: " + removedLast); // Should be C
        System.out.println("New size: " + deque.size()); // Should be 2

        String removedFirst = deque.removeFirst(); // Removes "A"
        System.out.println("Removed first item: " + removedFirst); // Should be A
        System.out.println("Final size: " + deque.size()); // Should be 1
    }
}