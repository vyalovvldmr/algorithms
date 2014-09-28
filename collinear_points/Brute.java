import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int len = Integer.parseInt(in.readLine());
        Point[] points = new Point[len];

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (int i = 0; i < len; i++) {
            String s = in.readLine().trim();
            Point p = new Point(
                Integer.parseInt(s.split("\\s+")[0]),
                Integer.parseInt(s.split("\\s+")[1]));
            points[i] = p;
            p.draw();

        }

        for (int i = 0; i <= len - 4; i++) {
            for (int j = i + 1; j <= len - 3; j++) {
                for (int l = j + 1; l <= len - 2; l++) {
                    for (int m = l + 1; m <= len -1; m++) {
                        double s1 = points[i].slopeTo(points[j]);
                        double s2 = points[i].slopeTo(points[l]);
                        double s3 = points[i].slopeTo(points[m]);

                        if (s1 == s2 && s1 == s3) {
                            Point p1 = points[i];
                            Point p2 = points[j];
                            Point p3 = points[l];
                            Point p4 = points[m];

                            Point[] array = {p1, p2, p3, p4};
                            Arrays.sort(array);

                            System.out.printf("%s -> %s -> %s -> %s\n",
                                array[0].toString(),
                                array[1].toString(),
                                array[2].toString(),
                                array[3].toString());

                            array[0].drawTo(array[3]);
                        }
                    }
                }
            }
        }
    }   
}