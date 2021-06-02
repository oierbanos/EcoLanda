package sistema_pantallas.tablas;

import styles.FontFactory;
import styles.RoundedBorder;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class HeaderRenderer implements TableCellRenderer {

    DefaultTableCellRenderer renderer;

    // Al inicializar ponemos los titulos en el centro.
    public HeaderRenderer(JTable table) {
        renderer = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        // Poner el titulo de la tabla con borde redondo y una fuente a corde.
        renderer.setBorder(new RoundedBorder(5));
        renderer.setFont(FontFactory.TITLE_FONT);

        return renderer;
    }
}