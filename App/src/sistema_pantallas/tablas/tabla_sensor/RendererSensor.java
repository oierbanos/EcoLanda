package sistema_pantallas.tablas.tabla_sensor;

import styles.FontFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class RendererSensor extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel componente = (JLabel) super.getTableCellRendererComponent(tabla, value, isSelected, hasFocus, row, column);

        componente.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));

        switch(column) {
            case 1:
            case 2:
            case 3:
            case 4:
                componente.setHorizontalAlignment(JLabel.CENTER);
                componente.setFont(FontFactory.TITLE_FONT);
                break;
            case 0:
                componente.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
                componente.setFont(FontFactory.PLAIN_TITLE_FONT);
                componente.setHorizontalAlignment(JLabel.LEFT);
                break;
            default: break;
        }

        return componente;
    }
}