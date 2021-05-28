package sistema_pantallas.pantallas_acciones;

import styles.ColorFactory;

import javax.swing.*;

public class PanelStock  extends JScrollPane {

    public PanelStock() {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(20);

        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setBackground(ColorFactory.BACKGROUND_COLOR);
    }
}
