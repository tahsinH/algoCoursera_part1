import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;


/**
 * Created by thassan on 9/27/18.
 */
public class Solver {


    private SearchNode solutionNode;
    private boolean solvable = false;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> theGame = new MinPQ<>();
        MinPQ<SearchNode> twinGame = new MinPQ<>();


        theGame.insert(new SearchNode(initial, 0, null));
        twinGame.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode curr = theGame.delMin();
        SearchNode currTwin = twinGame.delMin();

        while (!curr.mBoard.isGoal() && !currTwin.mBoard.isGoal()) {
            addValidNeighBours(curr, theGame);
            addValidNeighBours(currTwin, twinGame);

            curr = theGame.delMin();
            currTwin = twinGame.delMin();
        }

        if (currTwin.mBoard.isGoal()) {
            solutionNode = null;
            solvable = false;
        } else {
            solutionNode = curr;
            solvable = true;
        }
    }

    private void addValidNeighBours(SearchNode curr, MinPQ<SearchNode> pq)
    {
        SearchNode prev = curr.getPredecessor();
        for (Board aNeighbour : curr.mBoard.neighbors()) {
            if (prev == null || !aNeighbour.equals(prev.getBoard())) { // critical optimization, discard neighbour equal to predecessor
                pq.insert(new SearchNode(aNeighbour, curr.mMoves + 1, curr));
            }
        }
    }


    private class SearchNode implements Comparable<SearchNode> {
        private final Board mBoard;
        private int mMoves;
        private SearchNode mPredecessor;

        public SearchNode(final Board initial, int moves, final SearchNode predecessor) {
            mBoard = initial;
            mMoves = moves;
            mPredecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            int thisPriorityWithMovesAndPriorityFunc = mBoard.manhattan() + mMoves;
            int otherPriorityWithMovesAndPriorityFunc = other.mBoard.manhattan() + other.mMoves;

            if (thisPriorityWithMovesAndPriorityFunc < otherPriorityWithMovesAndPriorityFunc) {
                return -1;
            }

            if (thisPriorityWithMovesAndPriorityFunc == otherPriorityWithMovesAndPriorityFunc) {
                return 0;
            }
            return 1;
        }

        public Board getBoard() {
            return mBoard;
        }

        public SearchNode getPredecessor() {
            return mPredecessor;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        } else {
            return solutionNode.mMoves;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> theSolutionChain = new Stack<Board>();
        SearchNode node = solutionNode;
        while (node != null) {
            theSolutionChain.push(node.getBoard());
            node = node.getPredecessor();
        }
        return theSolutionChain;

    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int val;
            int[][] tiles = new int[n][n];
            int[][] aCopyOfTiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    val = in.readInt();
                    tiles[i][j] = val;
                    aCopyOfTiles[i][j] = val;
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            System.out.print("\n====The Board====\n");
            System.out.println(initial);

            Solver soln = new Solver(initial);
            System.out.println("The number of moves = " + soln.moves());

            System.out.print("\n====The Solution====\n");
            if (soln.isSolvable()) {
                for (Board aRunningSolStep : soln.solution()) {
                    System.out.print("\n====a step====\n");
                    System.out.println(aRunningSolStep);
                }
            } else {
                System.out.println("The puzzle is unsolvable");
            }


        }
    }
}
