package sistema_pantallas.pantallas_acciones.componentes;

import javafx.scene.layout.Pane;
import sistema_pantallas.pantallas_acciones.PanelHuerto;
import styles.FontFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AddImagen extends JPanel {

    PanelHuerto panelHuerto;
    File file;

    public AddImagen(PanelHuerto panelHuerto) {
        super(new FlowLayout());
        this.panelHuerto = panelHuerto;

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(crearInput());
    }

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
