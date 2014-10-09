import java.util.Comparator;

public class Solver {
    private SearchNode goal = null;

    private class SearchNode {
        private int movesCount;
        private Board board;
        private SearchNode prevNode;
        
        public SearchNode(Board b) {
            movesCount = 0;
            prevNode = null;
            board = b;
        }
    }

    private class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode v, SearchNode w) {
            int priority1 = v.board.manhattan() + v.movesCount;
            int priority2 = w.board.manhattan() + w.movesCount;
            
            if (priority1 > priority2) return 1;
            else if (priority1 < priority2) return -1;
            else return 0;
        }
    }

    /**
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        MinPQ<SearchNode> queue = initQueue(initial);
        MinPQ<SearchNode> twinQueue = initQueue(initial.twin());

        SearchNode minNode = queue.delMin();
        SearchNode twinMinNode = twinQueue.delMin();
        while (!minNode.board.isGoal() && !twinMinNode.board.isGoal()) {
            minNode = processNeighbors(minNode, queue);
            twinMinNode = processNeighbors(twinMinNode, twinQueue);
        }

        if (minNode.board.isGoal()) goal = minNode;
    }

    /**
     * is the initial board solvable?
     */
    public boolean isSolvable() {
        return goal != null;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if (goal == null) return -1;
        return goal.movesCount;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable())  return null;

        Stack<Board> stack = new Stack<Board>();
        SearchNode node = goal;

        while (node != null) {
            stack.push(node.board);
            node = node.prevNode;
        }

        return stack;
    }

    private MinPQ<SearchNode> initQueue(Board initial) {
        PriorityOrder order = new PriorityOrder();
        MinPQ<SearchNode> queue = new MinPQ<SearchNode>(order);
        SearchNode node = new SearchNode(initial);
        queue.insert(node);
        return queue;
    }

    private SearchNode processNeighbors(SearchNode node, MinPQ<SearchNode> queue) {
        for (Board b : node.board.neighbors()) {
            if ((node.prevNode == null) || !b.equals(node.prevNode.board)) {
                SearchNode newNode = new SearchNode(b);
                newNode.movesCount = node.movesCount + 1;
                newNode.prevNode = node;
                queue.insert(newNode);
            }
        }
        return queue.delMin();
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}