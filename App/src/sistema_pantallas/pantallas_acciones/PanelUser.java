package sistema_pantallas.pantallas_acciones;

import styles.ColorFactory;
import styles.ImageFactory;

import javax.swing.*;

/**
 * Panel de gestion del usuario.
 */
public class PanelUser extends JScrollPane {

    public PanelUser() {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setBackground(ColorFactory.BACKGROUND_COLOR);

        JLabel label = new JLabel(ImageFactory.createImageIcon(ImageFactory.IMAGE_USER));
        label.setIcon(ImageFactory.createImageIcon(ImageFactory.IMAGE_USER));
        this.setViewportView(label);
    }
}
