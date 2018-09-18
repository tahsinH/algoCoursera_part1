

import java.util.Iterator;

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

    private Item [] theItems;
    private int n;            // number of elements on queue

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        theItems = (Item []) new Object[1];
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
        if(n == theItems.length)
        {
            resize(2* theItems.length);
        }
        theItems[n++] = item;
    }

    /**
     * remove and return a random item
     *
     * @return
     */
    public Item dequeue() {
        throw new UnsupportedOperationException();
    }

    /**
     * return a random item (but do not remove it)
     *
     * @return
     */
    public Item sample() {
        throw new UnsupportedOperationException();
    }

    /**
     * return an independent iterator over items in random order
     *
     * @return
     */
    public Iterator<Item> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * unit testing
     */
    public static void main(String[] args) {

    }
}