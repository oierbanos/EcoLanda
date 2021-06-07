package sistema_pantallas.pantallas_acciones.componentes;

import styles.FontFactory;
import styles.RoundedBorder;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de seleccion de tipos.
 */
public class SelectorTipos extends JPanel {

    /**
     * Modelo de la lista que se va a mostrar.
     */
    DefaultListModel<String> modelo;
    /**
     * Lista donde se muestran los datos.
     */
    JList<String> lista;

    /**
     * Crear una nueva instancia de un selector de tipos.
     */
    public SelectorTipos() {
        super(new BorderLayout());
        this.add(crearPanelScroll());
    }

    /**
     * Crear el panel donde se muestran los elementos.
     * @return
     */
    private Component crearPanelScroll() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 0),
                new RoundedBorder(10)
        ));
        panel.setPreferredSize(new Dimension(300, 100));

        JPanel subpanel = new JPanel(new BorderLayout());
        subpanel.add(createTextField("Tipo de Productos"), BorderLayout.NORTH);
        subpanel.add(crearTiposBasicos(), BorderLayout.CENTER);
        panel.add(subpanel, BorderLayout.CENTER);

        subpanel = new JPanel(new BorderLayout());
        subpanel.add(createTextField("Productos Personalizados"), BorderLayout.NORTH);
        subpanel.add(crearTiposPersonalizados(), BorderLayout.CENTER);
        panel.add(subpanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crear zona de muestra de texto.
     * @param text Texto que se va a mostrar.
     * @return Un nuevo elemento de muestra de texto.
     */
    private JTextField createTextField(String text) {
        JTextField titulo = new JTextField(text);

        titulo.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        titulo.setHorizontalAlignment(JTextField.CENTER);
        titulo.setFont(FontFactory.TITLE_FONT);

        return  titulo;
    }

    /**
     * Crear panel de tipos basicos.
     * @return Nuevo panel de tipos basicos.
     */
    private Component crearTiposBasicos() {
        JScrollPane panel = new JScrollPane(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return panel;
    }

    /**
     * Crear panel de tipos personalizados.
     * @return Nuevo panel de tipos personalizados.
     */
    private Component crearTiposPersonalizados() {
        JScrollPane panel = new JScrollPane(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return panel;
    }
}
