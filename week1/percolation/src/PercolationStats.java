/**
 * Created by thassan on 8/8/18.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import javafx.util.Pair;


public class PercolationStats {


    private final int trials;
    private final double [] results;
    private final double mean;
    private final double stddev;

    /**
     * perform trials independent experiments on an n-by-n grid
     * @param n
     * @param trials
     */
    public PercolationStats(final int n, final int trials)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        if (trials <= 0)
        {
            throw new IllegalArgumentException("Number of Trials must be greater than 0");
        }

        this.trials = trials;
        results = new double[trials];

        for (int i = 0; i < trials; ++i)
        {
            Percolation perc = new Percolation(n);

            // System.out.println("running trial  " + i);

            int[] flatIdxSites = new int[n * n];
            for (int arrIdx = 0; arrIdx < flatIdxSites.length; arrIdx++)
            {
                flatIdxSites[arrIdx] = arrIdx;
            }

            StdRandom.shuffle(flatIdxSites);
            int flatIdxCounter = 0;
            while (!perc.percolates()) {
                Pair<Integer, Integer>  rowColPair = flatIdxToRowCol(flatIdxCounter, n);
                int row = rowColPair.getKey();
                int col = rowColPair.getValue();
                flatIdxCounter += 1;

                // System.out.println("the row " + row);
                // System.out.println("the col " + col);

                perc.open(row, col);
            }

            int numOfSitesOpenedOnOneTrial = perc.numberOfOpenSites();

            double singleResult = ((double) numOfSitesOpenedOnOneTrial)/ ((double) (n * n));
            results[i] = singleResult;
        }
        this.mean = StdStats.mean(results);
        this.stddev = StdStats.stddev(results);

    }


    private Pair<Integer, Integer> flatIdxToRowCol(final int flatIdx, final int size)
    {
        int row =  (flatIdx/ size) + 1;
        int col =  flatIdx % size + 1; // this is a 1 based row, col matrix
        return new Pair<>(row, col);
    }


    /**
     * sample mean of percolation threshold
     * @return
     */
    public double mean()
    {
        return this.mean;
    }

    /**
     * sample standard deviation of percolation threshold
     * @return
     */
    public double stddev()
    {
        return this.stddev;
    }

    /**
     * low  endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLo()
    {
        return this.mean + ((1.96 * this.stddev)/ Math.sqrt((double) trials));
    }

    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHi()
    {
        return this.mean + ((1.96 * this.stddev)/ Math.sqrt((double) trials));
    }

    /**
     * test client (described below)
     * @param args
     */
    public static void main(String[] args)
    {
        int n = new Integer(args[0]);
        int trials = new Integer(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);


        System.out.println("mean:\t\t\t\t = " + stats.mean());
        System.out.println("stddev:\t\t\t\t = " + stats.stddev());
        System.out.println("95% confidence interval:\t = " + stats.confidenceLo() + ", " + stats.confidenceHi());


    }
}