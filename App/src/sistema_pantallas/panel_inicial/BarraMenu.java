package sistema_pantallas.panel_inicial;

import app_launcher.EcoLanda;
import sistema_pantallas.gestion_pantallas.Gestor;
import styles.*;

import javax.swing.*;

public class BarraMenu extends JMenuBar {

    PanelPrincipal mainPanel;

    public BarraMenu(PanelPrincipal panelPrincipal) {
        this.mainPanel = panelPrincipal;

        this.add(crearSubmenuInicio());
        this.add(crearSubmenuGestion());
        this.add(Box.createHorizontalGlue());
        this.add(crearSubmenuPersonal());
    }

    private JMenu crearSubmenuInicio() {
        JMenu menu = new JMenu("Inicio");
        menu.setFont(FontFactory.MENU_FONT);

        JMenuItem item = new JMenuItem("Pantalla Inicio");
        item.setIcon(ImageFactory.createImageIcon(ImageFactory.START_ICON));
        item.addActionListener((e) -> {
            mainPanel.changePanel(Gestor.crearPanelInicio());
        });
        item.setToolTipText("Visitar la pantalla de inicio.");
        item.setAccelerator(KeyStroke.getKeyStroke("control I"));
        item.setFont(FontFactory.MENU_FONT);
        menu.add(item);

        return menu;
    }

    private JMenu crearSubmenuGestion() {
        JMenu menu = new JMenu("Gestión");
        menu.setFont(FontFactory.MENU_FONT);

        JMenuItem item = new JMenuItem("Gestión Cultivos");
        item.setIcon(ImageFactory.createImageIcon(ImageFactory.CULTIVO_ICON));
        item.addActionListener((e) -> {
            // Do something
        });
        item.setToolTipText("Gestionar el estado del huerto.");
        item.setAccelerator(KeyStroke.getKeyStroke("control C"));
        item.setFont(FontFactory.MENU_FONT);
        menu.add(item);

        item = new JMenuItem("Ver Stock");
        item.setIcon(ImageFactory.createImageIcon(ImageFactory.STOCK_ICON));
        item.addActionListener((e) -> {
            // Do something
        });
        item.setToolTipText("Visualizar productos guardados.");
        item.setAccelerator(KeyStroke.getKeyStroke("control S"));
        item.setFont(FontFactory.MENU_FONT);
        menu.add(item);

        item = new JMenuItem("Valores Sensores");
        item.setIcon(ImageFactory.createImageIcon(ImageFactory.SENSOR_ICON));
        item.addActionListener((e) -> {
            // Do something
        });
        item.setToolTipText("Ver los valores que han recogido los sensores.");
        item.setAccelerator(KeyStroke.getKeyStroke("control V"));
        item.setFont(FontFactory.MENU_FONT);
        menu.add(item);

        return menu;
    }

    private JMenu crearSubmenuPersonal() {
        JMenu menu = new JMenu("Area Personal");
        menu.setFont(FontFactory.MENU_FONT);

        JMenuItem item = new JMenuItem("Mi huerto");
        item.setIcon(ImageFactory.createImageIcon(ImageFactory.USER_ICON));
        item.addActionListener((e) -> {
            // Do something
        });
        item.setToolTipText("Ver los parámetros del perfil");
        item.setAccelerator(KeyStroke.getKeyStroke("control U"));
        item.setFont(FontFactory.MENU_FONT);
        menu.add(item);

        item = new JMenuItem("Cerrar Sesión");
        item.setIcon(ImageFactory.createImageIcon(ImageFactory.CERRAR_ICON));
        item.addActionListener((e) -> {
            // Do something
        });
        item.setToolTipText("Cerrar sesión");
        item.setAccelerator(KeyStroke.getKeyStroke("control esc"));
        item.setFont(FontFactory.MENU_FONT);
        menu.add(item);

        return menu;
    }
}
