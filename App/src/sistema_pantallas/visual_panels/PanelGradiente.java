package sistema_pantallas.visual_panels;

import styles.ColorFactory;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class PanelGradiente extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelGradiente(LayoutManager layout) {
        super(layout);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g; // Determinar el modo de renderizado
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();

        Color color1 = ColorFactory.BASE_COLOR;     // Verde
        Color color2 = ColorFactory.LIGHT_COLOR;    // Verde claro

        // Gradiente de arriba abajo
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
