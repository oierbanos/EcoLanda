package sistema_pantallas.pantallas_acciones.componentes;

import javafx.scene.layout.Pane;
import sistema_pantallas.pantallas_acciones.PanelHuerto;
import styles.FontFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Panel para añadir imagenes.
 */
public class AddImagen extends JPanel {

    /**
     * Panel donde se encuenra.
     */
    PanelHuerto panelHuerto;
    /**
     * Fichero con los tipos.
     */
    File file;

    /**
     * Crear una nueva instancia del panel de añadir imagen.
     * @param panelHuerto Panel donde se encuentra este panel.
     */
    public AddImagen(PanelHuerto panelHuerto) {
        super(new FlowLayout());
        this.panelHuerto = panelHuerto;

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(crearInput());
    }

    /**
     * Boton de input.
     * @return Un nuevo boton de input.
     */
    private JButton crearInput() {
        JButton boton = new JButton("Añadir Imagen");

        boton.addActionListener((e)->{
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
                panelHuerto.setMapImage(file);
            }
        });
        boton.setFont(FontFactory.BIG_BUTTON);

        return boton;
    }
}
