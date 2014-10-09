import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int dimension;
    private int[][] blocks;
    // coordinates of blank square
    private int x, y;

    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        dimension = blocks.length;
        this.blocks = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    y = i;
                    x = j;
                }
            }
        }
    }

    /**
     * board dimension N
     */
    public int dimension() {
        return dimension;
    }

    /**
     * number of blocks out of place
     */
    public int hamming() {
        int hamming = 0;
        int count = 1;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != count) hamming++;
                count++;
            }
        }
        return hamming;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int manhattan = 0;
        int count = 0;
        int xDistance, yDistance;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0) {
                    xDistance = Math.abs(
                        (blocks[i][j] - 1) / dimension - count / dimension);
                    yDistance = Math.abs(
                        (blocks[i][j] - 1) % dimension - count % dimension);
                    manhattan += xDistance + yDistance;
                }
                count++;
            }
        }
        return manhattan;
    }

    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        if (blocks[dimension - 1][dimension - 1] != 0) return false;
        return hamming() == 0;
    }

    /**
     * a boadr that is obtained by exchanging two adjacent blocks in the same row
     */
    public Board twin() {
        if (blocks[0][0] != 0 && blocks[0][1] != 0) {
            exchange(0, 0, 1, 0);
            Board board = new Board(blocks);
            exchange(0, 0, 1, 0);
            return board;
        } else {
            exchange(0, 1, 1, 1);
            Board board = new Board(blocks);
            exchange(0, 1, 1, 1);
            return board;
        }
    }

    /**
     * does this board equal y?
     */
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(blocks, that.blocks);
    }

    private void exchange(int a, int b, int m, int n) {
        int tmp = blocks[b][a];
        blocks[b][a] = blocks[n][m];
        blocks[n][m] = tmp;
    }

    private Board getNeighbor(int i, int j) {
        exchange(i, j, x, y);
        Board board = new Board(blocks);
        exchange(i, j, x, y);
        return board;
    }

    /**
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();

        if (x + 1 < dimension) neighbors.add(getNeighbor(x + 1, y));
        if (y + 1 < dimension) neighbors.add(getNeighbor(x, y + 1));
        if (x - 1 >= 0) neighbors.add(getNeighbor(x - 1, y));
        if (y - 1 >= 0) neighbors.add(getNeighbor(x, y - 1));

        return neighbors;
    }

    /**
     * string representation of this board (in the output format specified below)
     */
    public String toString() {
        String str = Integer.toString(dimension) + "\n";

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                str = str + String.format(
                    " %s", Integer.toString(blocks[i][j]));
            }
            str = str + "\n";
        }

        return str;
    }

    public static void main(String[] args) {
        Board board = new Board(new int[][]{{7, 5, 0}, {6, 4, 2}, {3, 8, 1}});
        Board board1= new Board(new int[][]{{7, 5, 0}, {6, 4, 2}, {3, 8, 1}});
        System.out.println(board.equals(board1));
        System.out.println(board.toString());
        System.out.println(board.twin().toString());
        System.out.println(board.hamming());
        System.out.println(board.manhattan());

        for (Board b : board.neighbors()) {
            System.out.println(b.toString());
        }
    }
}