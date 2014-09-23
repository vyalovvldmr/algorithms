import java.util.Iterator;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] collection;
    private int N = 0;
    private Random rnd = new Random();

    private class RandomizedIterator implements Iterator<Item> {
        private int len;
        private Item[] array;

        public RandomizedIterator() {
            len = N;
            array = (Item[]) new Object[len];
            for (int i = 0; i < len; i++) {
                array[i] = collection[i];
            }
        }

        public boolean hasNext() {
            return len != 0;
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            int rand_num = rnd.nextInt(len);
            Item item = array[rand_num];
            array[rand_num] = array[--len];
            array[len] = null;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        collection = (Item[]) new Object[1];
    }

    /**
     * is the queue empty?
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * return the number of items on the queue
     */
    public int size() {
        return N;
    }

    /**
     * add the item
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (N == collection.length) 
            resize(2 * collection.length);
        collection[N++] = item;
    }

    /**
     * delete and return a random item
     */
    public Item dequeue() {
        if (N == 0) throw new java.util.NoSuchElementException();
        int rand_num = rnd.nextInt(N);
        Item item = collection[rand_num];
        collection[rand_num] = collection[--N];
        collection[N] = null;
        if (N > 0 && N == collection.length / 4) 
            resize(collection.length / 2);
        return item;
    }

    /**
     * return (but do not delete) a random item
     */
    public Item sample() {
        if (N == 0) throw new java.util.NoSuchElementException();
        return collection[rnd.nextInt(N)];
    }

    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = collection[i];
        }
        collection = copy;
    }
}