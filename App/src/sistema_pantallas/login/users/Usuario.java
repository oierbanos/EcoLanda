package sistema_pantallas.login.users;

/**
 * Datos del usuario que ha iniciado sesión.
 */
public class Usuario {

    /**
     * Nombre de usuario que ha utilizado para iniciar sesión.
     */
    private String username;

    /**
     * Nombre propio del usuario.
     */
    private String nombre;

    /**
     * Apellido del usuario.
     */
    private String apellido;

    /**
     * Correo electronico del usuario.
     */
    private String correo;

    /**
     * Telefono del usuario.
     */
    private String telefono;

    /**
     * Dirección del huerto
     */
    private Direccion direccion;

    /**
     * Identificador del huerto dentro de la base de datos
     */
    private int huerto_id;

    /**
     * Crear un objeto de tipo usuario.
     * @param username  Nombre de usuario.
     * @param nombre    Nombre del usuario.
     * @param apellido  Apellido del usuario.
     * @param correo    Correo electronico del usuario.
     * @param telefono  Numero de telefono del usuario.
     * @param direccion Dirección del usuario.
     * @param huerto_id Identificador del huerto que tiene asociado.
     */
    public Usuario(String username, String nombre, String apellido, String correo, String telefono, Direccion direccion, int huerto_id) {
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.huerto_id = huerto_id;
    }

    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public int getHuerto_id() {
        return huerto_id;
    }
}
