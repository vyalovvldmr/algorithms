import java.util.Random;

public class PercolationStats {
    private double mean;
    private double deviation;
    private int T;

    public PercolationStats(int N, int t) {
        if (N <= 0 || t <= 0) throw new IllegalArgumentException();
        Random rnd = new Random();
        T = t;
        double[] openSiteFractions = new double[T];

        for (int k = 0; k < T; k++) {
            Percolation p = new Percolation(N);
            int i = 0;
            int j = 0;
            double openSiteAmount = 0;

            while (!p.percolates()) {
                i = rnd.nextInt(N) + 1;
                j = rnd.nextInt(N) + 1;
                if (!p.isOpen(i, j)) {
                    openSiteAmount++;
                    p.open(i, j);
                }                
            }
            openSiteFractions[k] = openSiteAmount / (N * N);
        }
        mean = StdStats.mean(openSiteFractions);
        deviation = StdStats.stddev(openSiteFractions);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return deviation;
    }

    public double confidenceLo() {
        return mean - ((1.96 * deviation) / Math.sqrt(T));
    }

    public double confidenceHi() {
        return mean + ((1.96 * deviation) / Math.sqrt(T));
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(
            Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLo());
        System.out.println(ps.confidenceHi());
    }
}