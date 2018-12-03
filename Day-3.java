package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[][] fabric = initFabric();

        // Silly way to keep track, better way to do it with an Object
        List<Integer> ids = new ArrayList<>();
        List<Integer> fromLeft = new ArrayList<>();
        List<Integer> fromTop = new ArrayList<>();
        List<Integer> wides = new ArrayList<>();
        List<Integer> talls = new ArrayList<>();

        int xCount = 0;
        List<String> claims = new ArrayList<>();

        // Input
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        while (input != null && !input.equals("")) {
            claims.add(input);
            input = in.nextLine();
        }

        // Logic
        for (String claim : claims) {
            int indexOfAt = claim.indexOf('@');
            int indexOfComa = claim.indexOf(',');
            int indexOfTwoDots = claim.indexOf(':');
            int indexOfX = claim.indexOf('x');

            String id = claim.substring(1, indexOfAt - 1);
            ids.add(Integer.valueOf(id));
            int fromLeftEdge = Integer.parseInt(claim.substring(indexOfAt + 2, indexOfComa));
            fromLeft.add(fromLeftEdge);
            int fromTopEdge = Integer.parseInt(claim.substring(indexOfComa + 1, indexOfTwoDots));
            fromTop.add(fromTopEdge);
            int wide = Integer.parseInt(claim.substring(indexOfTwoDots + 2, indexOfX));
            wides.add(wide);
            int tall = Integer.parseInt(claim.substring(indexOfX + 1));
            talls.add(tall);

            for (int row = fromTopEdge, endRow = 0; endRow < tall; endRow++, row++) {
                for (int col = fromLeftEdge, endCol = 0; endCol < wide; endCol++, col++) {
                    if (fabric[row][col].equals("X")) {
                        continue;
                    }
                    if (!fabric[row][col].equals(".")) {
                        fabric[row][col] = "X";
                        xCount++;
                    } else
                        fabric[row][col] = id;
                }
            }
        }

        // Print X counts
        System.out.println(xCount);

        // Check for winner
        for (int i = 0; i < ids.size(); i++) {
            if (isWinner(fabric, fromTop.get(i), fromLeft.get(i), wides.get(i), talls.get(i))) {
                System.out.println(ids.get(i));
            }
        }
    }

    private static String[][] initFabric() {
        String[][] fabric = new String[1000][1000];
        for (int i = 0; i < fabric.length; i++) {
            for (int j = 0; j < fabric[0].length; j++) {
                fabric[i][j] = ".";
            }
        }
        return fabric;
    }

    private static void printFabric(String[][] fabric) {
        for (int i = 0; i < fabric.length; i++) {
            for (int j = 0; j < fabric[0].length; j++) {
                System.out.print(fabric[i][j]);
            }
            System.out.println();
        }
    }

    private static boolean isWinner(String[][] fabric, int fromTopEdge, int fromLeftEdge, int wide, int tall) {
        boolean wins = true;
        for (int row = fromTopEdge, endRow = 0; endRow < tall; row++, endRow++) {
            for (int col = fromLeftEdge, endCol = 0; endCol < wide; col++, endCol++) {
                if (fabric[row][col].equals("X"))
                    wins = false;
            }
        }
        return wins;
    }

}
