package sistema_pantallas.pantallas_acciones;

import sistema_pantallas.pantallas_acciones.componentes.*;
import styles.ColorFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

public class PanelHuerto extends JScrollPane {

    Point start = null;
    Point end = null;

    PropertyChangeSupport conector;

    JPanel mainPanel;
    JPanel panelHuerto;

    public PanelHuerto(PropertyChangeListener listener) {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.conector = new PropertyChangeSupport(this);
        this.conector.addPropertyChangeListener(listener);

        this.setBackground(Color.white);
        this.setBorder(null);

        this.getVerticalScrollBar().setUnitIncrement(20);
        this.setViewportView(crearPanelVentana());
    }

    private Component crearPanelVentana() {
        mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(crearPanelHuerto(), BorderLayout.CENTER);
        mainPanel.add(new AddImagen(this), BorderLayout.SOUTH);
        mainPanel.add(new SelectorTipos(), BorderLayout.EAST);

        return mainPanel;
    }

    private Component crearPanelHuerto() {
        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(ColorFactory.GROUND_COLOR);

        panel.add(crearPanelHuertoInterno());

        return panel;
    }

    private Component crearPanelHuertoInterno() {
        panelHuerto = new JPanel(new BorderLayout());

        panelHuerto.setBackground(ColorFactory.GROUND_COLOR_LIGHT);
        panelHuerto.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        panelHuerto.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) {
                start = new Point(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (start != null) {
                    end = new Point(e.getX(), e.getY());
                    conector.firePropertyChange("Repaint", "none", "new");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        });

        return panelHuerto;
    }

    public void setMapImage(File image) {
        if (panelHuerto != null) {
            mainPanel.remove(panelHuerto);
            this.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
