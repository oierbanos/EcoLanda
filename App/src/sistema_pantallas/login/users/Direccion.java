package sistema_pantallas.login.users;

public class Direccion {

    /**
     * Calle donde se encuentra el huerto.
     */
    private String calle;

    /**
     * El piso se utilizará en el caso de que el punto de venta esté en un edificio.
     */
    private String piso;

    /**
     * Localidad donde se encuentra el huerto.
     */
    private String localidad;

    /**
     * Provincia donde se encuentra el huerto.
     */
    private String codigo_postal;

    /**
     * Direccion de residencia de un usuario.
     * @param calle Calle de residencia.
     * @param piso Piso (Numero + Puerta).
     * @param localidad Localidad de residencia.
     * @param codigo_postal Codigo postal.
     */
    public Direccion(String calle, String piso, String localidad, String codigo_postal) {
        this.calle = calle;
        this.piso = piso;
        this.localidad = localidad;
        this.codigo_postal = codigo_postal;
    }
}
