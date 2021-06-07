package errors;

/**
 * Excepcion de tiempo.
 */
public class TimeException extends Exception{

    /**
     * Excepcion que ocurre cuando se asigna un tiempo con valores incorrectos.
     * @param msg Mensaje de error.
     */
    public TimeException(String msg) {
        super(msg);
    }
}
