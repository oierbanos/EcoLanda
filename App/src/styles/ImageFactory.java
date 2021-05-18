package styles;

import java.awt.Image;
import javax.swing.*;

public class ImageFactory {
    /** Icono inicial de la aplicación **/
    public static final String MAIN_ICON = "Media/Icons/Icon.jpg";

    /** Iconos de la barra de menus **/
    public static final String START_ICON = "Media/Icons/Inicio.png";
    public static final String CULTIVO_ICON = "Media/Icons/Inicio.png";
    public static final String STOCK_ICON = "Media/Icons/Inicio.png";
    public static final String SENSOR_ICON = "Media/Icons/Inicio.png";
    public static final String USER_ICON = "Media/Icons/Inicio.png";
    public static final String CERRAR_ICON = "Media/Icons/Inicio.png";

    /** Imagenes de la pantalla de inicio **/
    public static final String IMAGEN_INICIO = "Media/Img/Inicio.png";
    public static final String IMAGEN_LOGO = "Media/Img/Logo.png";

    /** Iconos del menu de la izquierda **/
    public static final String IMAGEN_HUERTO = "Media/Icons/huerto.png";
    public static final String IMAGEN_STOCK = "Media/Icons/stock.png";
    public static final String IMAGEN_SENSOR = "Media/Icons/sensor.png";
    public static final String IMAGEN_USUARIO = "Media/Icons/usuario.png";

    /**
     * Generar un ImageIcon a partir de una ruta.
     * @param route Ruta de la imagen.
     * @return Un ImageIcon con la imagen enrutada.
     */
    public static ImageIcon createImageIcon(String route) {
        return new ImageIcon(route);
    }

    /**
     * Generar un Image a partir de una ruta.
     * @param route Ruta de la imagen.
     * @return Un Image con la imagen enrutada.
     */
    public static Image createImage(String route) {
        return new ImageIcon(route).getImage();
    }
}
