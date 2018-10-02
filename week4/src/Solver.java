import edu.princeton.cs.algs4.MinPQ;

/**
 * Created by thassan on 9/27/18.
 */
public class Solver {


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial.manhattan() == 0)
        {

        }
        MinPQ<Board> theBoard  = new MinPQ<>();
        throw new UnsupportedOperationException();

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        throw new UnsupportedOperationException();

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        throw new UnsupportedOperationException();

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        throw new UnsupportedOperationException();

    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
    }
    }
