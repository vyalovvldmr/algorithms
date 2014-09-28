import java.util.Arrays;
import java.util.HashMap; 
import java.util.Map; 

public class Fast {
    private static void print(Point[] array) {
        System.out.printf("%s", array[0].toString());
        for (int k = 1; k < array.length; k++) {
            System.out.printf(" -> %s", array[k].toString());
        }
        System.out.printf("\n");
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int len = Integer.parseInt(in.readLine());
        Point[] pointsOriginOrder = new Point[len];
        Point[] points = new Point[len];
        Map<String, Integer> segmentHashes = new HashMap<String, Integer>();

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (int i = 0; i < len; i++) {
            String s = in.readLine().trim();
            Point p;
            try {
                p = new Point(
                    Integer.parseInt(s.split("\\s+")[0]),
                    Integer.parseInt(s.split("\\s+")[1]));
            } catch (NumberFormatException e) {
                i--;
                continue;
            }
            points[i] = p;
            pointsOriginOrder[i] = p;
            p.draw();

        }

        for (int i = 0; i < len; i++) {
            Point p = pointsOriginOrder[i];
            int segmentLength = 1;
            Arrays.sort(points, p.SLOPE_ORDER);

            for (int j = 1; j < len; j++) {
                if (points[j - 1].slopeTo(p) == points[j].slopeTo(p)) {
                    segmentLength++;
                } 
                if (points[j - 1].slopeTo(p) != points[j].slopeTo(p) || j == len - 1) {
                    if (segmentLength >= 3 ) {
                        if (j == len - 1 && !(points[j - 1].slopeTo(p) != points[j].slopeTo(p))) j++;
                        Point[] subArray = new Point[segmentLength + 1];
                        subArray[0] = p;
                        for (int k = 1; k <= segmentLength; k++) {
                            subArray[k] = points[j - 1 - segmentLength + k];
                        }

                        Arrays.sort(subArray);

                        String hashKey = subArray[0].toString() + Double.toString(subArray[0].slopeTo(subArray[1]));
                        if (!segmentHashes.containsKey(hashKey)) {
                            segmentHashes.put(hashKey, 1);
                            subArray[0].drawTo(subArray[segmentLength]);

                            print(subArray);
                        }
                    }
                    segmentLength = 1;
                }
            }
        }
    }
}