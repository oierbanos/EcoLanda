package formaters;

public class CharFormater {

    public static String transformToString(char[] array) {
        StringBuilder value = new StringBuilder();

        for (char c : array) {
            value.append(c);
        }
        return value.toString();
    }
}
