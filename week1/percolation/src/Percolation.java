/**
 * Created by thassan on 8/8/18.
 */



import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 *  A percolation system is modeled by n- by -n grid. Each site is either open or blocked.
 *  A full site is an open site that can be connected to an open site in the top row
 *  via a chain of neighboring (left, right, up, down) open sites.
 *  We say the system percolates if there is a full site in the bottom row. In other words,
 *  a system percolates if we fill all open sites connected
 *  to the top row and that process fills some open site on the bottom row.
 *  (For the insulating/metallic materials example, the open sites correspond to metallic materials,
 *  so that a system that percolates has a metallic path from top to bottom, with full sites conducting.
 */


public class Percolation {

    private int[][] grid; // the grid in question
    private final WeightedQuickUnionUF uf; // the internal data structure
    private int numOpenSites = 0; // number of open sites
    private int theN;    // the size of the grid (in 1-d sense)
    private final int virtualTop; // the uf index corresponding to Virtual top
    private final int virtualBottom; // the uf index corresponding to Virtual bottom

    /**
     * create n-by-n grid, with all sites blocked
     * @param n the size of the grid/system for which we need to calculate the percolation characteristic
     */
    public Percolation(final int n)
    {
        //  the grid
        //    0   1   2   3   4
        // -
        // 0 _1  _2  _3  _4  _5
        // -
        //  1 _6  _7  _8  _9  _10
        // -
        //  2
        // -
        //  3
        // -
        //  4
        // _

        // the UF original
        // _1 _2 ... _n _n+1 _n*2
        // The modified Uf accounting for 2 virtual sites
        // _0 _1 _2 ... _n*2 (_n*2 + 1)

        // the grid indexes start at 0 , however the api from the coursera test site, expects a 1 based index.
        // so the grid's 1 based indexes are accounted by (idx -1) or (row - 1) (col - 1);

        if (n <= 0) {
            throw new IllegalArgumentException("the size of grid must be greater than 0");
        }

        theN = n;
        grid = new int[n][n]; // language spec guarantees that the array is initialized at 0;
        uf = new WeightedQuickUnionUF(((n * n) + 2)); // accounting 2 for 2 virtual sites on top and bottom.
        virtualTop = 0;
        virtualBottom = (n * n)+ 1;
    }

    /**
     * open site (row, col) if it is not open already
     * @param row the row element index
     * @param col the col element index
     */
    public void open(final int row, final int col) {
        // open the grid position.
        // the API starts with 1 based indexing
        validateIdx(row, col);

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = 1;
            numOpenSites++;
        }


        int siteIdx = rowColToFlatIdx(row, col);
        if (row == 1)
        {
            uf.union(virtualTop, siteIdx);
        }
        if (row == theN)
        {
            uf.union(virtualBottom, siteIdx);
        }
        connectToSurroundingSites(row, col);
    }

    /**
     * In the uf data structure look around,
     * and see whether adjacent (in grid terms, neighbouring top, bottom, left, right)
     * are also open, and in that case, call connect to connect the current site with
     * its adjacent already open sites.
     * @param row the row element index
     * @param col the col element index
     */
    private void connectToSurroundingSites(final int row, final int col)
    {
        int ufIdx = rowColToFlatIdx(row, col);
        if ((row != 1) && isOpen(row - 1, col)) {
            int topUfIdx = rowColToFlatIdx(row - 1, col);
            uf.union(ufIdx, topUfIdx);
        }
        if ((row != theN) && isOpen(row + 1, col)) {
            int bottomUfIdx = rowColToFlatIdx(row + 1, col);
            uf.union(ufIdx, bottomUfIdx);
        }
        if ((col != 1) && isOpen(row, col - 1)) {
            int leftUfIdx = rowColToFlatIdx(row, col - 1);
            uf.union(ufIdx, leftUfIdx);
        }
        if ((col != theN) && isOpen(row, col + 1)) {
            int rightUfIdx = rowColToFlatIdx(row, col + 1);
            uf.union(ufIdx, rightUfIdx);
        }
    }

    /**
     * validates whether the row and columns are infact within bounds of the array
     * @param row the row element index
     * @param col the column element index
     */
    private void validateIdx(final int row, final int col)
    {
        if (row <= 0 || row > theN) throw new IllegalArgumentException("row index out of bounds");
        if (col <= 0 || col > theN) throw new IllegalArgumentException("col index out of bounds");
    }



    /**
     * is site (row, col) open?
     * @param row the row element index
     * @param col the col element index
     * @return whether the site is open or not
     */
    public boolean isOpen(final int row, final int col)
    {
        validateIdx(row, col);
        return (grid[row - 1][col - 1] != 0);
    }

    /**
     * is site (row, col) full?
     * @param row the row element index
     * @param col the col element index
     * @return
     */
    public boolean isFull(final int row, final int col) {
        // A full site is an open site that can be connected to an open site in
        // the top row via a chain of neighboring (left, right, up, down) open sites.
        validateIdx(row, col);
        int siteInQuestion = rowColToFlatIdx(row, col);
        return uf.connected(siteInQuestion, virtualTop);
    }

    /**
     * // number of open sites
     * @return
     */
    public int numberOfOpenSites()
    {
        return numOpenSites;
    }

    /**
     * does the system percolate?
     * @return
     */
    public boolean percolates()
    {
        return uf.connected(virtualTop, virtualBottom);
    }

    /**
     * Converts the x-row and y-col of a 2D arr to 1D array idx
     * so that , we can map the 2D arr idx to WeightedQuickUnionFind's 1D idx
     * @param row the row index
     * @param col the column index
     * @return a linear flat idx , which is unique given a row and col
     */
    private  int rowColToFlatIdx(final int row, final int col)
    {

        //  the grid
        //    0   1   2   3   4
        // -
        // 0 _1  _2  _3  _4  _5
        // -
        // 1 _6  _7  _8  _9  _10
        // -
        // 2
        // -
        // 3
        // -
        // 4

        // the grid indexes start at 0 , however the api from the coursera test site, expects a 1 based index.
        // so the grid's 1 based indexes are accounted by (idx -1) or (row - 1) (col - 1);

        //  the grid
        //    1   2   3   4   5
        // -
        // 1 _1  _2  _3  _4  _5
        // -
        // 2 _6  _7  _8  _9  _10
        // -
        // 3 _11 _12 _13
        // -
        // 4
        // -
        // 5

        // the UF original
        // _1 _2 ... _n _n+1 _n*2
        // The modified Uf accounting for 2 virtual sites
        // _0 _1 _2 ... _n*2 (_n*2 + 1)
        // the _0 accounts for the top virtual site,
        // whereas the (_n*2+1) accounts for the bottom virtual site

        // the uf data structure has 2n + 2 accounting for 2 virtual sites worth of element

        // Row 1, col 1 = idx _1
        // Row 1, col 2 = idx _2
        // Row 3, col 2 = idx _12
        // Row 3, col 3 = idx _13
        // Row 5, col 5 = idx _25
        validateIdx(row, col);
        return ((row - 1) * theN) + col;
    }

    /**
     *  // test client (optional)
     * @param args
     */
    public static void main(String[] args)
    {


    }


}
