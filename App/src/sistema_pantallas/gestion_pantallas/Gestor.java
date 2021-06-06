package sistema_pantallas.gestion_pantallas;

import external_conexion.database.QuerySelector;
import sistema_pantallas.login.users.Usuario;
import sistema_pantallas.panel_inicial.PanelPrincipal;
import sistema_pantallas.pantallas_acciones.*;

import javax.swing.*;

public class Gestor {

    public static final String GESTIONAR_HUERTO = "huerto";
    public static final String GESTIONAR_STOCK = "stock";
    public static final String GESTIONAR_SENSORES = "sensor";
    public static final String GESTIONAR_USER = "usuario";

    /**
     * Crear el panel de incio.
     * @return Un panel de inicio.
     */
    public static JScrollPane crearPanelInicio() { return new PanelInicio(); }

    /**
     * Crear un panel de gestion del huerto.
     * @param panel El panel principal.
     * @return Un panel de gestion del huerto.
     */
    public static JScrollPane crearPanelGestionHuerto(PanelPrincipal panel) { return new PanelHuerto(panel); }

    /**
     * Crear un panel de gestion del stock.
     * @param parent Panel que mostrara los mensajes de error.
     * @param user El usuario que ha inciado sesion.
     * @param conector Conector con la base de datos.
     * @return Un nuevo panel de stock.
     */
    public static JScrollPane crearPanelGestionStock(JFrame parent, Usuario user, QuerySelector conector) {
        return new PanelStock(parent, user.getHuertoId(), conector);
    }

    /**
     * Crear un nuevo panel para gestionar los datos de los sensores.
     * @param parent Panel que mostrara los mensajes de error.
     * @param user El usuario que ha inciado sesion.
     * @param conector Conector con la base de datos.
     * @return Un nuevo panel de gestion de sensores.
     */
    public static JScrollPane crearPanelGestionSensores(JFrame parent, Usuario user, QuerySelector conector) {
        return new PanelSensores(parent, conector, user.getHuertoId());
    }

    /**
     * Crear un panel donde se gestionara los parametros que tengan que ver con el usuario
     * y el punto de comercio online.
     * @return Un nuevo panel de gestion de usuario.
     */
    public static JScrollPane crearPanelGestionUser() {
        return new PanelUser();
    }
}
