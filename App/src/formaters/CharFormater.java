package formaters;

public class CharFormater {

    /**
     * Transformar un array de caracteres a un string.
     * @param array El array de caracteres a transformar.
     * @return El string creado.
     */
    public static String transformToString(char[] array) {
        StringBuilder value = new StringBuilder();

        for (char c : array) {
            value.append(c);
        }
        return String.valueOf(value);
    }
}
