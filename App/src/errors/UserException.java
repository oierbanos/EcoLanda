package errors;

/**
 * Excepcion de usuario.
 */
public class UserException extends Exception {

    /**
     * Excepcion generada durante el login de un usuario.
     * @param error Mensaje de error.
     */
    public UserException(String error) {
        super(error);
    }
}
