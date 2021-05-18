package sistema_pantallas.gestion_pantallas;

import sistema_pantallas.pantallas_acciones.*;

import javax.swing.*;

public class Gestor {

    public static final String GESTIONAR_HUERTO = "huerto";
    public static final String GESTIONAR_STOCK = "stock";
    public static final String GESTIONAR_SENSORES = "sensor";
    public static final String GESTIONAR_USER = "usuario";

    public static JScrollPane crearPanelInicio() { return new PanelInicio(); }

    public static JScrollPane crearPanelGestionHuerto() { return new PanelHuerto(); }

    public static JScrollPane crearPanelGestionStock() {
        return new PanelStock();
    }

    public static JScrollPane crearPanelGestionSensores() {
        return new PanelSensores();
    }

    public static JScrollPane crearPanelGestionUser() {
        return new PanelUser();
    }
}
