import java.util.Arrays;
import java.util.HashMap; 
import java.util.Map; 

public class Fast {
    private static Point[] pointsOriginOrder;
    private static Point[] points;

    private static void init(String[] args) {
        In in = new In(args[0]);
        Point point;
        String str;

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        int len = Integer.parseInt(in.readLine());
        pointsOriginOrder = new Point[len];
        points = new Point[len];

        for (int i = 0; i < len; i++) {
            str = in.readLine().trim();
            try {
                point = new Point(
                    Integer.parseInt(str.split("\\s+")[0]),
                    Integer.parseInt(str.split("\\s+")[1]));
            } catch (NumberFormatException e) {
                i--;
                continue;
            }
            points[i] = point;
            pointsOriginOrder[i] = point;
            point.draw();

        }
        in.close();
    }

    private static void print(Point[] array) {
        System.out.printf("%s", array[0].toString());
        for (int k = 1; k < array.length; k++) {
            System.out.printf(" -> %s", array[k].toString());
        }
        System.out.printf("\n");
    }

    public static void main(String[] args) {
        Map<String, Integer> segmentHashes = new HashMap<String, Integer>();
        double slope1, slope2;
        Point point;
        int segmentLength;
        Point[] subArray;
        String hashKey;

        init(args);

        for (int i = 0; i < points.length; i++) {
            point = pointsOriginOrder[i];
            segmentLength = 1;

            Arrays.sort(points, point.SLOPE_ORDER);

            for (int j = 1; j < points.length; j++) {
                slope1 = points[j - 1].slopeTo(point);
                slope2 = points[j].slopeTo(point);

                if (slope1 == slope2) {
                    segmentLength++;
                }

                if (slope1 != slope2 || j == points.length - 1) {
                    if (segmentLength >= 3 ) {
                        if (j == points.length - 1 && !(slope1 != slope2)) j++;

                        subArray = new Point[segmentLength + 1];
                        subArray[0] = point;
                        for (int k = 1; k <= segmentLength; k++) {
                            subArray[k] = points[j - 1 - segmentLength + k];
                        }

                        Arrays.sort(subArray);

                        hashKey = subArray[0].toString() + Double.toString(
                            subArray[0].slopeTo(subArray[segmentLength]));

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