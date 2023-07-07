import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;



class Result {

    /*
     * Complete the 'areAlmostEquivalent' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. STRING_ARRAY s
     *  2. STRING_ARRAY t
     */

    public static List<String> areAlmostEquivalent(List<String> s, List<String> t) {
        // Write your code here
        int numberTracker = 0;
        int arrayNumber = s.size();

        for (int i = 0; i < arrayNumber; i++) {
            String string1 = s.get(i);
            String string2 = t.get(i);

            if (string1.length() != string2.length()) {
                System.out.println("NO");
            }

            for (int j = 0; j < string1.length(); j++) {
                if (string1.charAt(j) != string2.charAt(j)) {
                    numberTracker++;

                    if(numberTracker > 3) {
                        System.out.println("NO");
                    }
                }
            }
        }
        return Collections.singletonList("YES");
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int sCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> s = IntStream.range(0, sCount).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        int tCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> t = IntStream.range(0, tCount).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        List<String> result = Result.areAlmostEquivalent(s, t);

        bufferedWriter.write(
                result.stream()
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
