package sistema_pantallas.login;

import external_conexion.database.QuerySelector;
import formaters.CharFormater;
import styles.ColorFactory;
import styles.FontFactory;
import styles.ImageFactory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;

public class PantallaLogin extends JFrame {

    /**  Ancho por defecto de la pantalla */
    public final static int DEFAULT_WIDTH = 400;

    /** Alto por defecto de la pantalla */
    public final static int DEFAULT_HEIGHT = 600;

    ControladorLogin controlador;

    JTextField username;
    JPasswordField password;

    public PantallaLogin(QuerySelector conector, PropertyChangeListener listener) {
        // Titulo de la aplicación
        super("EcoLanda Login");

        // Crear el controlador
        controlador = new ControladorLogin(listener, conector);

        // Determinar tamaño de la pantalla
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = (int) toolkit.getScreenSize().getWidth();       // Ancho
        int height = (int) toolkit.getScreenSize().getHeight();     // Alto

        // Poner icono a la aplicación
        this.setIconImage(ImageFactory.createImage(ImageFactory.MAIN_ICON));

        // Colocar pantalla en el centro
        this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // Crear panel principal
        this.setContentPane(crearPanelLogin());

        // Cambiar el color del fondo
        this.setBackground(ColorFactory.BACKGROUND_COLOR);

        // Si se cierra la ventana se cierra la app
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private Container crearPanelLogin() {
        JPanel panel  = new JPanel(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(crearImagenUser(), BorderLayout.NORTH);               // Imagen de un usuario
        panel.add(crearPanelRellenoFormulario(), BorderLayout.CENTER);  // Zona para introducir los datos
        panel.add(crearBotonConfirmar(), BorderLayout.SOUTH);           // Botones confirmar/cancelar

        return panel;
    }

    private Container crearBotonConfirmar() {
        JPanel panel  = new JPanel(new GridLayout(2, 1));
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JButton boton = crearBoton("Iniciar Sesion", 120);
        boton.addActionListener( (l)-> {
            controlador.iniciarSesion(this, "Login", username.getText(),
                                       CharFormater.transformToString(password.getPassword()));
        } );
        panel.add(crearPanelBoton(boton));

        boton = crearBoton("Cancelar", 100);
        boton.addActionListener( (l)-> {
            controlador.iniciarSesion(this, "Cancelar", "", "");
        } );
        panel.add(crearPanelBoton(boton));

        return panel;
    }

    private JButton crearBoton(String text, int w) {
        JButton boton = new JButton(text);

        boton.setPreferredSize(new Dimension(w, 25));
        boton.setFont(FontFactory.BASE_FONT);

        return boton;
    }

    private JPanel crearPanelBoton(JButton boton) {
        JPanel panel = new JPanel();

        panel.setBackground(ColorFactory.BACKGROUND_COLOR);
        panel.add(boton);

        return panel;
    }

    private Container crearPanelRellenoFormulario() {
        JPanel panel  = new JPanel();

        username = new JTextField(20);
        password = new JPasswordField(15);
        password.setEchoChar('*');

        JPanel panelGrid = new JPanel(new GridLayout(2,1,0,0));
        panelGrid.setBorder(new LineBorder(ColorFactory.BACKGROUND_COLOR, 15, true));
        panelGrid.setBackground(ColorFactory.BACKGROUND_COLOR);

        panelGrid.add(crearTextField());
        panelGrid.add(crearJPasswordField());

        panel.add(panelGrid);
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        return panel;
    }

    private Component crearTextField() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JPanel subpanel = new JPanel();
        subpanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(5, 5, 10, 5),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(Color.black, 2), "Nombre Usuario")
                ),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(15, 10, 5, 10),
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.black),
                                BorderFactory.createEmptyBorder(8, 8, 8, 8)
                        )
                )
        ));
        subpanel.setBackground(ColorFactory.BACKGROUND_COLOR);

        subpanel.add(username);
        panel.add(subpanel);

        return panel;
    }

    private Component crearJPasswordField() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JPanel subpanel = new JPanel();
        subpanel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JCheckBox mostrar = new JCheckBox();
        mostrar.setIcon(new ImageIcon(
                ImageFactory.escalarImagen(ImageFactory.ICONO_HIDE,200,200,25,25))
        );
        mostrar.setSelectedIcon(new ImageIcon(
                ImageFactory.escalarImagen(ImageFactory.ICONO_SHOW,200,200,25,25))
        );
        mostrar.setBackground(ColorFactory.BACKGROUND_COLOR);
        mostrar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(0, 3, 0, 3)
        ));
        mostrar.addActionListener((l)->{
            if (mostrar.isSelected()) password.setEchoChar((char)0);
            else password.setEchoChar('*');
        });
        password.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black),
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)
                ));
        subpanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 5, 5, 5),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Contraseña"),
                        BorderFactory.createEmptyBorder(5, 5, 10, 5)
                )
        ));
        subpanel.add(password);
        subpanel.add(mostrar);
        panel.add(subpanel);

        return panel;
    }

    private Container crearImagenUser() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JLabel lbimagen  = new JLabel(new ImageIcon(
                makeRoundedCorner(ImageFactory.escalarImagen(
                        ImageFactory.IMAGEN_USER, 750, 750, 200, 200), 220)
        ));
        lbimagen.setPreferredSize(new Dimension(200,200));
        lbimagen.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(lbimagen);
        return panel;
    }

    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(ColorFactory.BACKGROUND_COLOR);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        return output;
    }
}
