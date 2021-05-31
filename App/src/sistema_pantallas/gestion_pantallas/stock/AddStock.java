package sistema_pantallas.gestion_pantallas.stock;

import styles.FontFactory;

import javax.swing.*;
import java.awt.*;

public class AddStock extends JDialog {

    public final static int DEFAULT_WIDTH = 300;
    public final static int DEFAULT_HEIGHT = 500;

    public AddStock(JFrame parentComponent, String tiutlo, boolean modo) {
        super(parentComponent, tiutlo, modo);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = (int) toolkit.getScreenSize().getWidth();
        int height = (int) toolkit.getScreenSize().getHeight();

        this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        this.setContentPane(crearPanelVentana());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private Container crearPanelVentana() {
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(panelInput(), BorderLayout.CENTER);
        panel.add(panelBotones(), BorderLayout.SOUTH);

        return panel;
    }

    private Component panelInput() {
        JPanel panel = new JPanel(new GridLayout(3, 1));

        panel.add(crearPanelSeleccionTipo());
        panel.add(crearPanelCantidad());
        panel.add(crearPanelFecha());

        return panel;
    }

    private Component crearPanelSeleccionTipo() {
        return null;
    }

    private Component crearPanelCantidad() {
        return null;
    }

    private Component crearPanelFecha() {
        return null;
    }

    private Component panelBotones() {
        JPanel panel = new JPanel(new GridLayout(1, 2));

        JButton boton = crearBoton("Aceptar");
        boton.addActionListener(e -> {
            System.out.println("Aceptar");
            AddStock.this.dispose();
        });

        JPanel pBoton = new JPanel();
        pBoton.add(boton);
        panel.add(pBoton);

        boton = crearBoton("Cancelar");
        boton.addActionListener(e -> AddStock.this.dispose());

        pBoton = new JPanel();
        pBoton.add(boton);
        panel.add(pBoton);

        return panel;
    }

    private JButton crearBoton(String title) {
        JButton boton = new JButton(title);

        boton.setPreferredSize(new Dimension(125, 40));
        boton.setFont(FontFactory.NORMAL_BUTTON);

        return boton;
    }
}