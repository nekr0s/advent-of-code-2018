import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DayTwoDotTwo {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        List<String> listOfStrings = new ArrayList<>();
        Map<Character, Integer> map = new HashMap<>();

        String input = in.nextLine();
        while (input != null && !input.equals("")) {
            listOfStrings.add(input);
            input = in.nextLine();
        }

        String result = null;
        for (int i = 0; i < listOfStrings.size(); i++) {
            for (int j = i + 1; j < listOfStrings.size(); j++) {
                result = findCloseMatch(listOfStrings.get(i), listOfStrings.get(j));
                if (result != null) break;
            }
            if (result != null) break;
        }

        System.out.println(result);

    }

    private static String findCloseMatch(String string1, String string2) {
        int differ = string1.length();
        int differenceIndex = -1;
        for (int i = 0; i < string1.length(); i++) {
            if (string1.charAt(i) == string2.charAt(i))
                differ--;
            else
                differenceIndex = i;
        }

        if (differ == 1) {
            return string1.substring(0, differenceIndex).concat(string1.substring(differenceIndex + 1));
        } else
            return null;
    }

}
