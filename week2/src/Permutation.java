/**
 * Created by thassan on 9/6/18.
 */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

/**
 * Write a client program Permutation.java that takes an integer k as a command-line argument;
 * reads in a sequence of strings from standard input using StdIn.readString();
 * and prints exactly k of them, uniformly at random. Print each item from the sequence at most once.

 % more distinct.txt                        % more duplicates.txt
 A B C D E F G H I                          AA BB BB BB BB BB CC CC

 % java-algs4 Permutation 3 < distinct.txt   % java-algs4 Permutation 8 < duplicates.txt
 C                                               BB
 G                                               AA
 A                                               BB
                                                 CC

 % java-algs4 Permutation 3 < distinct.txt       BB
 E                                               BB
 F                                               CC
 G                                               BB
 */

public class Permutation {
    public static void main(String[] args) {

        RandomizedQueue<String> rQueue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            rQueue.enqueue(item);
        }

        for (int i = 0 ; i < k; i++)
        {
            StdOut.println (rQueue.dequeue());
        }

    }
}

