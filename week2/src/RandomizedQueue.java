

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by thassan on 9/6/18.
 * Note: that the assignment requires that
 * we cannot directly use either java.util.LinkedList or java.util.ArrayList.
 * We can however use an array or homegrown linkedlist.
 * In the following implementation an array  is used.
 * We can measure the performance as following
 * RandomizedQueue
 * Non-iterator operations      Constant amortized time
 * Iterator constructor         linear in current # of items
 * Other iterator operations    Constant worst-case time
 * Non-iterator memory use      Linear in current # of items
 * Memory per iterator          Linear in current # of items
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] theItems;
    private int n;            // number of elements on queue

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        theItems = (Item[]) new Object[1];
    }

    /**
     * is the randomized queue empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * return the number of items on the randomized queue
     *
     * @return
     */
    public int size() {
        return n;
    }


    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = theItems[i];
        }
        theItems = temp;
    }


    /**
     * add the item
     *
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == theItems.length) {
            resize(2 * theItems.length);
        }
        theItems[n++] = item;
    }

    /**
     * remove and return a random item
     *
     * @return
     */
    public Item dequeue() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        Integer shuffledNum = StdRandom.uniform(0, n);
        Item toBeRemoved = theItems[shuffledNum];
        theItems[shuffledNum] = theItems[n - 1];
        theItems[n - 1] = null;

        if (n > 0 && n == theItems.length / 4) {
            resize(theItems.length / 2);
        }

        n--;
        return toBeRemoved;
    }

    /**
     * return a random item (but do not remove it)
     *
     * @return
     */
    public Item sample() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        Integer shuffledNum = StdRandom.uniform(0, n);
        Item toBeRemoved = theItems[shuffledNum];
        return toBeRemoved;
    }

    /**
     * return an independent iterator over items in random order
     *
     * @return
     */
    public Iterator<Item> iterator() {
        return new RandomArrIterator();
    }

    private class RandomArrIterator implements Iterator<Item> {

        int[] order;
        int idx = 0;

        public RandomArrIterator() {
            order = new int[n];
            for (int i = 0; i < n; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return idx < n;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return theItems[order[idx++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * unit testing
     */
    public static void main(String[] args) {
        // unit test.. tested manually...

    }
}