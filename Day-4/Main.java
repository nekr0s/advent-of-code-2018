import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        List<String> timestamps = new ArrayList<>();

        // Input
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        while (input != null && !input.equals("")) {
            timestamps.add(input);
            input = in.nextLine();
        }

        List<MyTimestamp> entries = new ArrayList<>();
        for (String timestamp : timestamps) {
            String justDate = getDateString(timestamp);
            String command = getCommand(timestamp);
            char identification = getIdentification(command);
            try {
                Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(justDate);
                entries.add(new MyTimestamp(dateTime, command, identification));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        entries.sort(MyTimestamp::compareTo);

        for (MyTimestamp entry : entries) {
            System.out.println(entry);
        }

        String[][] matrix = initMatrix(entries);
        System.out.println();
        String bestId = findBestId(matrix);
        System.out.println(bestId);
    }

    private static String getDateString(String timestamp) {
        return timestamp.substring(1, 17);
    }

    private static String getCommand(String timestamp) {
        return timestamp.substring(19);
    }

    private static char getIdentification(String command) {
        return command.charAt(0);
    }

    private static String[][] initMatrix(List<MyTimestamp> timestamps) {
        String[][] matrix = new String[timestamps.size()][60];
        int day = 0;
        int lastDate = timestamps.get(0).getDate().getDate();
        String currentId = null;
        int currentFellAsleepMinute = 0;
        int currentWokeUpMinute = 0;
        for (MyTimestamp timestamp : timestamps) {
            if (lastDate != timestamp.getDate().getDate()) {
                day++;
                lastDate = timestamp.getDate().getDate();
            }

            if (timestamp.isBeingShift()) {
                currentId = timestamp.getId();
                continue;
            }
            if (timestamp.isFellAsleep()) {
                currentFellAsleepMinute = timestamp.getDate().getMinutes();
                continue;
            }

            if (timestamp.isWokeUp()) {
                currentWokeUpMinute = timestamp.getDate().getMinutes();

                for (int minute = currentFellAsleepMinute;
                     minute < currentWokeUpMinute; minute++) {
                    matrix[day][minute] = currentId;
                }
            }
        }
        return matrix;

    }

    private static String findBestId(String[][] matrix) {
        // ID to Minute to MinuteSeen
        Map<String, Map<Integer, Integer>> idToCount = new HashMap<>();
        for (int day = 0; day < matrix.length; day++) {
            for (int minute = 0; minute < matrix[0].length; minute++) {
                String id = matrix[day][minute];
                if (id != null) {
                    if (idToCount.containsKey(id)) {
                        if (idToCount.get(id).containsKey(minute)) {
                            idToCount.get(id).put(minute, idToCount.get(id).get(minute) + 1);
                        } else {
                            idToCount.get(id).put(minute, 1);
                        }
                    } else {
                        idToCount.put(id, new HashMap<>());
                        idToCount.get(id).put(minute, 1);
                    }
                }
            }
        }
        int bestSum = 0;
        String bestId = null;
        int currentSum = 0;
        for (Map.Entry<String, Map<Integer, Integer>> entry : idToCount.entrySet()) {
            for (Map.Entry<Integer, Integer> smallEntry : entry.getValue().entrySet()) {
                currentSum += smallEntry.getValue();
            }
            if (currentSum > bestSum) {
                bestSum = currentSum;
                bestId = entry.getKey();
            }
            currentSum = 0;
        }
        int bestMinute = 0;
        int bestMinuteCount = 0;
        String newBestId = null;
        for (Map.Entry<String, Map<Integer, Integer>> entry : idToCount.entrySet()) {
            for (Map.Entry<Integer, Integer> smallEntry : entry.getValue().entrySet()) {
                if (smallEntry.getValue() > bestMinuteCount) {
                    bestMinuteCount = smallEntry.getValue();
                    bestMinute = smallEntry.getKey();
                    newBestId = entry.getKey();
                }
            }
        }
//        for (Map.Entry<Integer, Integer> entry : idToCount.get(bestId).entrySet()) {
//            if (entry.getValue() > bestMinuteCount) {
//                bestMinuteCount = entry.getValue();
//                bestMinute = entry.getKey();
//            }
//        }
        return "help";
    }

}
