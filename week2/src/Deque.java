import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;


/**
 * Created by thassan on 9/6/18.
 * Note: that the assignment requires that
 * we cannot directly use either java.util.LinkedList or java.util.ArrayList.
 * We can however use an array or homegrown linkedlist.
 * In the following implementation a homegrown doubly linked list is used.
 * We can measure the performance as following
 * Deque
 * Non-iterator operations      Constant worst-case time
 * Iterator constructor         Constant worst-case time
 * Other iterator operations    Constant worst-case time
 * Non-iterator memory use      Linear in current # of items
 * Memory per iterator          Constant
 */
public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int numOfElements = 0; // number of elements in the deque.

    /**
     * construct an empty deque
     */
    public Deque() {

        head = null;
        tail = null;
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    /**
     * is the deque empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * return the number of items on the deque
     *
     * @return
     */
    public int size() {
        return numOfElements;
    }

    /**
     * add the item to the front
     *
     * @param aItem
     */
    public void addFirst(Item aItem) {

        if (aItem == null) {
            throw new IllegalArgumentException();
        }

        if (this.head == null) {
            head = new Node();
            head.item = aItem;

            head.prev = null;
            head.next = null;

            tail = head; // tail and head point to the same node in this case

        } else {
            // atleast one node is present in the deque


            Node oldFirst = head;
            head = new Node();
            head.item = aItem;
            head.next = oldFirst;
            head.prev = null;
            oldFirst.prev = head;
        }

        numOfElements++;

    }

    /**
     * add the item to the tail
     *
     * @param aItem
     */
    public void addLast(Item aItem) {
        if (aItem == null) {
            throw new IllegalArgumentException();
        }

        if (head == null) {
            head = new Node();
            head.item = aItem;

            head.prev = null;
            head.next = null;


            tail = head; // tail and head point to the same element in this case
        } else {
            Node oldEnd = tail;

            tail = new Node();
            tail.item = aItem;
            tail.next = null;
            tail.prev = oldEnd;

            oldEnd.next = tail;
        }

        numOfElements++;
    }

    /**
     * remove and return the item from the front
     *
     * @return
     */
    public Item removeFirst() {
        if (numOfElements == 0) {
            throw new NoSuchElementException();
        }
        Item aItem = head.item;

        if (numOfElements == 1) {
            head = null;
            tail = head;
        } else {
            head = head.next;
            head.prev = null;
        }
        numOfElements--;
        return aItem;
    }

    /**
     * remove and return the item from the tail
     *
     * @return
     */
    public Item removeLast() {
        if (numOfElements == 0) {
            throw new NoSuchElementException();
        }

        Item aItem = tail.item;
        if (numOfElements == 1) {
            tail = null;
            head = tail;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        numOfElements--;
        return aItem;
    }

    /**
     * return an iterator over items in order from front to tail
     *
     * @return
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item aItem = current.item;
            current = current.next;
            return aItem;
        }
    }

    /**
     * // unit testing mostly
     *
     * @param args
     */
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("D");
        deque.addFirst("C");
        deque.addFirst("B");
        deque.addFirst("A");
        deque.addLast("E");
        deque.removeLast();
        deque.removeFirst();


        StdOut.println("result should be B C D");
        for (String item : deque) {
            StdOut.println(item);
        }

        StdOut.println("======================");

        Deque<String> anotherDeque = new Deque<String>();

        anotherDeque.addFirst("S");
        anotherDeque.addFirst("R");
        anotherDeque.addFirst("Q");

        anotherDeque.removeFirst();
        anotherDeque.removeFirst();
        anotherDeque.removeFirst();
        // result should be 0
        StdOut.println(anotherDeque.size());

        anotherDeque.addFirst("F");
        // result should be F
        for (String item : anotherDeque) {
            StdOut.println(item);
        }

        StdOut.println("======================");

        Deque<Integer> cDeque = new Deque<Integer>();
        cDeque.addLast(0);
        cDeque.addLast(1);
        cDeque.addLast(2);
        cDeque.addLast(3);
        cDeque.addLast(4);

        StdOut.println("result should be 0 1 2 3 4");

        for (int item : cDeque) {
            StdOut.println(item);
        }

        cDeque.removeFirst();     //==> 0

        StdOut.println("result should be 1 2 3 4");
        for (int item : cDeque) {
            StdOut.println(item);
        }

        cDeque.addLast(6);
        cDeque.addLast(7);
        cDeque.addLast(8);
        cDeque.removeFirst();     // ==> 1

        StdOut.println("result should be 2 3 4 6 7 8");
        for (int item : cDeque) {
            StdOut.println(item);
        }
        StdOut.println("======================");

        Deque<Integer> dDeque = new Deque<Integer>();
        dDeque.addFirst(1);
        StdOut.println(dDeque.isEmpty());         //==> false
        dDeque.removeLast();
        StdOut.println(dDeque.isEmpty());        //==> true

    }
}