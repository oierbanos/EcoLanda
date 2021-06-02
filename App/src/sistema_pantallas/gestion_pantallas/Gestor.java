package sistema_pantallas.gestion_pantallas;

import external_conexion.database.Query_Selector;
import sistema_pantallas.gestion_pantallas.stock.GestorStock;
import sistema_pantallas.login.users.Usuario;
import sistema_pantallas.panel_inicial.PanelPrincipal;
import sistema_pantallas.pantallas_acciones.*;

import javax.swing.*;

public class Gestor {

    public static final String GESTIONAR_HUERTO = "huerto";
    public static final String GESTIONAR_STOCK = "stock";
    public static final String GESTIONAR_SENSORES = "sensor";
    public static final String GESTIONAR_USER = "usuario";

    public static JScrollPane crearPanelInicio() { return new PanelInicio(); }

    public static JScrollPane crearPanelGestionHuerto(PanelPrincipal panel) { return new PanelHuerto(panel); }

    public static JScrollPane crearPanelGestionStock(JFrame parent, Usuario user, Query_Selector conector) {
        return new PanelStock(parent, user.getHuerto_id(), conector);
    }

    public static JScrollPane crearPanelGestionSensores(JFrame parent, Usuario user, Query_Selector conector) {
        return new PanelSensores(parent, conector, user.getHuerto_id());
    }

    public static JScrollPane crearPanelGestionUser() {
        return new PanelUser();
    }
}
