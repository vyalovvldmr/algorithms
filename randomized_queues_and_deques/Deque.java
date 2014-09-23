import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null)
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * is the deque empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the deque
     */
    public int size() {
        return size;
    }

    /**
     * insert the item at the front
     */
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        if (isEmpty()) {
            last = first;
            first.next = null;            
        } else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        size++;
    }

    /**
     * insert the item at the end
     */
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
            last.prev = null;            
        } else {
            last.prev = oldLast;
            oldLast.next = last;
        }
        size++;
    }

    /**
     * delete and return the item at the front
     */
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first != null)
            first.prev = null;
        else
            last = null;
        size--;
        return item;
    }

    /**
     * delete and return the item at the end
     */
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        if (last != null)
            last.next = null;
        else
            first = null;
        size--;
        return item;
    }

    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
}