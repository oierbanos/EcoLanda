package sistema_pantallas.visual_panels;

import styles.ImageFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Panel con imagenes.
 */
public class PanelImagen extends PanelGradiente {

    /**
     * Imagen del panel.
     */
    ImageIcon mainImage, iconImage;

    /**
     * Crear una nueva instancia de un panel con imagen.
     */
    public PanelImagen() {
        super(new BorderLayout());

        this.setBackground(Color.white);
        this.mainImage = ImageFactory.createImageIcon(ImageFactory.IMAGEN_INICIO);  // Imagen de fondo
        this.iconImage = ImageFactory.createImageIcon(ImageFactory.IMAGEN_LOGO);    // Logo
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Colocar las imagenes de fondo
        g.drawImage(mainImage.getImage(), this.getWidth()/2 - mainImage.getIconWidth()/2, this.getHeight()/2 - mainImage.getIconHeight()/2, null);
        g.drawImage(iconImage.getImage(), this.getWidth() - iconImage.getIconWidth() - this.getWidth()/50, this.getHeight()/50, null);
    }
}
