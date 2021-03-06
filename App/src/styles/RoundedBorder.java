package styles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

/**
 * Borde redondeado.
 */
public class RoundedBorder implements Border {

	/**
	 * Radio de los bordes del panel.
	 */
	private final int radius;

	/**
	 * Crear una nueva instancia de un borde redondeado.
	 * @param radio
	 */
	public RoundedBorder(int radio) {
		this.radius = radio;
	}
	
	@Override
	public Insets getBorderInsets(Component arg0) {
		return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		g.setColor(Color.gray);
		g.drawRoundRect(x, y, width-1, height-1, radius, radius);
	}
}
