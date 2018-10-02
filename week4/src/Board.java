import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;


public class Board {
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    private final int[][] tiles;
    private final int n; // the n dimension,
    private int manhattanDist = 0;
    private int numberOfMovesMadeSoFar = 0;


    public Board(int[][] blocks) {

        // square array, arr.length() would suffice.
        // otherwise, look at following:
        // https://stackoverflow.com/questions/5958186/multidimensional-arrays-lengths-in-java
        n = blocks.length;

        tiles = new int[n][n];
        // make a deep copy
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }
    }


    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int hfunc = 0;
        int currTiles;
        for (int p = 0; p < n; p++) {
            for (int q = 0; q < n; q++) {
                currTiles = tiles[p][q];
                if ((currTiles != 0) &&
                        (currTiles != ((p * n) + (q + 1))))

                {
                    hfunc++;
                }
            }
        }
        return hfunc + numberOfMovesMadeSoFar;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattanDist == 0) {
            int[][] ij_pairOfGoalBoard = getGoalBoard_ijPair();
            int currTileManhattan = 0;
            int tempManhattan = 0;
            int i_goal;
            int j_goal;
            for (int p = 0; p < n; p++) {
                for (int q = 0; q < n; q++) {
                    int currTile = tiles[p][q];
                    if (currTile != 0) {
                        i_goal = ij_pairOfGoalBoard[currTile][0];
                        j_goal = ij_pairOfGoalBoard[currTile][1];
                        currTileManhattan = Math.abs(p - i_goal) + Math.abs(q - j_goal);
                        tempManhattan += currTileManhattan;
                    }
                }
            }
            manhattanDist = tempManhattan + numberOfMovesMadeSoFar;
        } //else return the cached value
        return manhattanDist;
    }

    private int[][] getGoalBoard_ijPair() {
        int i_goal;
        int j_goal;
        int[][] ij_pairOfGoalBoard = new int[n * n][2];
        for (int k = 1; k < n * n; k++) {
            int quotient = k / n;
            int remainder = k % n;
            if (remainder == 0) {
                i_goal = quotient - 1;
                j_goal = n - 1;
            } else {
                i_goal = quotient;
                j_goal = remainder - 1;
            }
            ij_pairOfGoalBoard[k][0] = i_goal;
            ij_pairOfGoalBoard[k][1] = j_goal;
        }
        return ij_pairOfGoalBoard;
    }

    // is this board the goal board?
    public boolean isGoal() {
        throw new UnsupportedOperationException();

//        return  true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] aTwin = new int[n][n];

        // make a deep copy
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                aTwin[i][j] = tiles[i][j];
            }
        }

        outerloop:
        for (int p = 0; p < n; p++) {
            for (int q = 0; q < n; q++) {
                if (aTwin[p][q] != 0) {
                    aTwin = swapWithNonZeroNeighbour(p, q, aTwin);
                    break outerloop;
                }
            }
        }
        return new Board(aTwin);
    }


    private int[][] swapWithNonZeroNeighbour(int row, int col, int[][] twinTiles) {
        if ((row - 1 >= 0) && twinTiles[row - 1][col] != 0) {
            swap(row - 1, col, row, col, twinTiles);
        } else if (col + 1 < n && twinTiles[row][col + 1] != 0) {
            swap(row, col + 1, row, col, twinTiles);
        } else if (row + 1 < n && twinTiles[row + 1][col] != 0) {
            swap(row + 1, col, row, col, twinTiles);
        } else if (col - 1 >= 0 && twinTiles[row][col - 1] != 0) {
            swap(row, col - 1, row, col, twinTiles);
        } else {
            throw new IllegalArgumentException("Cannot find a valid non zero neighbour");
        }
        return twinTiles;
    }

    private int[][] swap(int row1, int col1, int row2, int col2, int[][] twinTiles) {
        int temp = twinTiles[row1][col1];
        twinTiles[row1][col1] = twinTiles[row2][col2];
        twinTiles[row2][col2] = temp;
        return twinTiles;
    }


    // does this board equal y?
    public boolean equals(Object y) {
        throw new UnsupportedOperationException();

        //    return  true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        throw new UnsupportedOperationException();
/*
        Stack<Board> theBoard = new Stack<>();
        theBoard.push(this);
        return theBoard;
  */
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            System.out.print("\n====The Board====\n");
            System.out.println(initial);
            System.out.print("\n====manhattan distance====\n");
            System.out.print(initial.manhattan());
            System.out.print("\n====hamming distance====\n");
            System.out.print(initial.hamming());
            System.out.print("\n====The twin====\n");
            System.out.println(initial.twin());
            System.out.print("\n====The immutable board====\n");
            System.out.println(initial);
        }
    }

}

