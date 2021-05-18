package sistema_pantallas.pantallas_acciones.dibujables;

import java.awt.*;

public class Rectangulo implements Dibujable {

    private final Point start_point;
    private final Point end_point;

    public Rectangulo(Point start, Point end) {
        start_point = start;
        end_point = end;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D gr = (Graphics2D) g;

        gr.setColor(Color.red);
        gr.fillRect(
                start_point.x, start_point.y,
                end_point.x - start_point.x,
                end_point.y - start_point.y
        );
    }
}
