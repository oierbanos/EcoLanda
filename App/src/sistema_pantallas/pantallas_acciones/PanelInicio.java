package sistema_pantallas.pantallas_acciones;

import sistema_pantallas.visual_panels.PanelImagen;
import styles.ColorFactory;
import styles.FontFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PanelInicio extends JScrollPane {

    public PanelInicio() {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setBackground(Color.white);
        this.setBorder(null);

        this.getVerticalScrollBar().setUnitIncrement(20);
        this.setViewportView(crearPanelVentana());
    }

    private Component crearPanelVentana() {
        PanelImagen panel = new PanelImagen();
        panel.setBackground(Color.white);

        JLabel label = new JLabel("EcoLanda.eus©");
        label.setFont(FontFactory.LINK_FONT);
        label.setPreferredSize(new Dimension(200, 40));
        label.setForeground(ColorFactory.BASE_COLOR);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/oierbanos/EcoLanda"));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                label.setForeground(ColorFactory.HIGHLIGHT_COLOR);
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                label.setForeground(ColorFactory.BASE_COLOR);
            }

            @Override
            public void mousePressed(MouseEvent arg0) {}

            @Override
            public void mouseReleased(MouseEvent arg0) {}
        });

        panel.add(label, BorderLayout.SOUTH);

        return panel;
    }
}
