package main;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static String initInput() {
        return "183, 157\n" +
                "331, 86\n" +
                "347, 286\n" +
                "291, 273\n" +
                "285, 152\n" +
                "63, 100\n" +
                "47, 80\n" +
                "70, 88\n" +
                "333, 86\n" +
                "72, 238\n" +
                "158, 80\n" +
                "256, 140\n" +
                "93, 325\n" +
                "343, 44\n" +
                "89, 248\n" +
                "93, 261\n" +
                "292, 250\n" +
                "240, 243\n" +
                "342, 214\n" +
                "192, 51\n" +
                "71, 92\n" +
                "219, 63\n" +
                "240, 183\n" +
                "293, 55\n" +
                "316, 268\n" +
                "264, 151\n" +
                "68, 98\n" +
                "190, 288\n" +
                "85, 120\n" +
                "261, 59\n" +
                "84, 222\n" +
                "268, 171\n" +
                "205, 134\n" +
                "80, 161\n" +
                "337, 326\n" +
                "125, 176\n" +
                "228, 122\n" +
                "278, 151\n" +
                "129, 287\n" +
                "293, 271\n" +
                "57, 278\n" +
                "104, 171\n" +
                "330, 69\n" +
                "141, 141\n" +
                "112, 127\n" +
                "201, 151\n" +
                "331, 268\n" +
                "95, 68\n" +
                "289, 282\n" +
                "221, 359\n";
    }

    public static void main(String[] args) {
        String[] humanInput = initInput().split("\n");
        Map<Integer, Point> points = new HashMap<>();

        int maxX = 0;
        int maxY = 0;
        int count = 0;
        for (String aHumanInput : humanInput) {
            String[] coordinates = aHumanInput.split(", ");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            points.put(count++, new Point(x, y));
            if (x > maxX)
                maxX = x;
            if (y > maxY)
                maxY = y;
        }

        int[][] grid = new int[maxX + 1][maxY + 1];
        Map<Integer, Integer> regions = new HashMap<>();

        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                int bestNum = findBestNum(x, y, points);
                grid[x][y] = bestNum;
                Integer total = regions.get(bestNum);
                if (total == null)
                    total = 1;
                else
                    total = total + 1;
                regions.put(bestNum, total);
            }
        }
        // remove infinite
        for (int x = 0; x <= maxX; x++) {
            int bad = grid[x][0];
            regions.remove(bad);
            bad = grid[x][maxY];
            regions.remove(bad);
        }
        for (int y = 0; y <= maxY; y++) {
            int bad = grid[0][y];
            regions.remove(bad);
            bad = grid[maxX][y];
            regions.remove(bad);
        }

        // find biggest
        int biggest = 0;
        for (int size : regions.values()) {
            if (size > biggest)
                biggest = size;
        }
        System.out.println("Biggest: " + biggest);

        int inArea = 0;

        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                int size = 0;
                for (int i = 0; i < count; i++) {
                    Point p = points.get(i);
                    int dist = calcManhattanValue(x, y, p.x, p.y);
                    size += dist;
                }

                if (size < 10000) {
                    inArea++;
                }
            }
        }
        System.out.println("Area Size: " + inArea);
    }

    private static int findBestNum(int x, int y, Map<Integer, Point> points) {
        int bestNum = -1;
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            int dist = calcManhattanValue(x, y, p.x, p.y);
            if (dist < best) {
                best = dist;
                bestNum = i;
            } else if (dist == best) {
                bestNum = -1;
            }
        }
        return bestNum;
    }

    private static int calcManhattanValue(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - y1) + Math.abs(x2 - y2);
    }
}
