package styles;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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

    /** Imagenes inicio de sesión **/
    public static final String IMAGEN_USER = "Media/Img/user.png";
    public static final String ICONO_HIDE = "Media/Img/mostrar.png";
    public static final String ICONO_SHOW = "Media/Img/esconder.png";

    /** Imagenes de la pantalla de inicio **/
    public static final String IMAGEN_INICIO = "Media/Img/Inicio.png";
    public static final String IMAGEN_LOGO = "Media/Img/Logo.png";

    /** Iconos del menu de la izquierda **/
    public static final String IMAGEN_HUERTO = "Media/Icons/huerto.png";
    public static final String IMAGEN_STOCK = "Media/Icons/stock.png";
    public static final String IMAGEN_SENSOR = "Media/Icons/sensor.png";
    public static final String IMAGEN_USUARIO = "Media/Icons/usuario.png";

    /** Imagenes sensores **/
    public static final String IMAGEN_TEMPERATURA = "Media/Img/termometro.png";
    public static final String IMAGEN_HUMEDAD = "Media/Img/humedad.png";
    public static final String IMAGEN_BUSCAR = "Media/Img/buscar.png";

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

    /**
     * Escalar una imagen al tamaño que se le haya especificado.
     * @param path Ruta de la imagen
     * @param width Anchura de la imagen
     * @param height Altura de la imagen
     * @param newWidth Anchura a la que se quiere escalar
     * @param newHeight Altura a la que se quiere escalar
     * @return La imagen escalada
     */
    public static BufferedImage escalarImagen(String path, int width, int height, int newWidth, int newHeight) {
        File imgFile = new File(path);

        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        try {
            buffImg = ImageIO.read(imgFile);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        BufferedImage copy = new BufferedImage(buffImg.getWidth(), buffImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = copy.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, copy.getWidth(), copy.getHeight());
        g2d.drawImage(buffImg, 0, 0, null);
        g2d.dispose();

        return resizeImage(copy, newWidth,newHeight);
    }

    /**
     * Cambiar el tamaño de una imagen
     * @param originalImage Imagen original
     * @param targetWidth Anchura que se quiere obtener
     * @param targetHeight Altura que se quiere obtener
     * @return La imagen escalada
     */
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();

        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        return resizedImage;
    }
}
