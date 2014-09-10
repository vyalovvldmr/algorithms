public class Percolation {
    private WeightedQuickUnionUF ufPercolates;
    private WeightedQuickUnionUF ufIsFull;
    private boolean[] siteStates;
    private int N;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        N = n;
        ufPercolates = new WeightedQuickUnionUF((N * N) + 2);
        ufIsFull = new WeightedQuickUnionUF((N * N) + 1);
        siteStates = new boolean[(N * N) + 1];

        for (int i = 1; i <= N * N; i++) {
            siteStates[i] = false;
        }
    }

    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            siteStates[getIndex(i, j)] = true;
            union(i, j, i + 1, j);
            union(i, j, i - 1, j);
            union(i, j, i, j + 1);
            union(i, j, i, j - 1);
        }

        if (i == 1) {
            // union with top virtual site
            ufPercolates.union(0, j);
            ufIsFull.union(0, j);
        }

        if (i == N) {
            // union with bottom virtual site
            ufPercolates.union((N * N) + 1, (N * N) - (N - j));
        }
    }

    public boolean isOpen(int i, int j) {
        return siteStates[getIndex(i, j)];
    }

    public boolean isFull(int i, int j) {
        if (isOpen(i, j))
            return ufIsFull.connected(0, getIndex(i, j));
        else
            return false;
    }

    public boolean percolates() {
        return ufPercolates.connected(0, (N * N) + 1);
    }

    private int getIndex(int i, int j) {
        if ((1 > i || i > N) || (1 > j || j > N)) {
            throw new IndexOutOfBoundsException();
        } else {
            return (i * N) - (N - j);
        }
    }

    private void union(int i, int j, int x, int y) {
        boolean isOpened;
        try {
            isOpened = isOpen(x, y);
        } catch (IndexOutOfBoundsException e) {
            isOpened = false;
        }
        if (isOpened) {
            ufPercolates.union(getIndex(i, j), getIndex(x, y));
            ufIsFull.union(getIndex(i, j), getIndex(x, y));
        }
    }
}