package sistema_pantallas.pantallas_acciones;

import javax.swing.*;
import java.awt.*;

public class PanelHuerto extends JScrollPane {

    public PanelHuerto() {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.setBackground(Color.white);
        this.setBorder(null);

        this.getVerticalScrollBar().setUnitIncrement(20);
        //this.setViewportView(crearPanelVentana());
    }
}
