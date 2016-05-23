import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by toneyc on 5/17/16.
 */
public class Python {

    public static ArrayList<String> executeScript(String file, int p1, int p2) {
        ArrayList<String> lines = new ArrayList<String>();

        try {
            System.out.println("python " + file + " " + p1 + " " + p2);
            Process p = Runtime.getRuntime().exec("python " + file + " " + p1 + " " + p2);

            Scanner input = new Scanner(p.getInputStream());
            while (input.hasNext()) {
                lines.add(input.nextLine());
            }
        } catch (IOException e) {
            lines.add("Problem occurred.");
        }
        return lines;
    }

    public static String getHTML(String filePath, int p1, int p2) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<html>\n");
        buffer.append("<head>\n");
        buffer.append("</head>\n");
        buffer.append("<body>\n");
        buffer.append("<h2>Script: " + filePath + "</h2>\n");
        buffer.append("<ul>\n");
        ArrayList<String> s = executeScript(filePath, p1, p2);
        for (int i = 0; i < s.size(); i++)
            buffer.append("<li>" + s.get(i) + "</li>");
        buffer.append("</ul>\n");
        buffer.append("</body>\n");
        buffer.append("</html>\n");
        return buffer.toString();
    }
}