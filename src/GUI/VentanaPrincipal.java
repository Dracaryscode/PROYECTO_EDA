package GUI;

import Models.Candidato;
import Models.Eleccion;
import TDA.ListaEnlazada;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JPanel panelPrincipal;
    private PanelElecciones panelElecciones;
    private PanelCandidatos panelCandidatos;
    private PanelVotos panelVotos;
    private PanelReportes panelReportes;

    private ListaEnlazada<Eleccion> electionList; // Lista global para elecciones
    private ListaEnlazada<Candidato> listaCandidatos; // Lista global para candidatos

    public VentanaPrincipal() {
        setTitle("Sistema de Gestión Electoral");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700); // Tamaño ajustado
        setLocationRelativeTo(null);

        // Inicializar listas enlazadas
        listaCandidatos = new ListaEnlazada<>();
        electionList = new ListaEnlazada<>();

        // Inicializar componentes de la ventana
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout());
        setContentPane(panelPrincipal);

        // Crear paneles con las listas compartidas
        panelElecciones = new PanelElecciones(electionList, listaCandidatos);
        panelCandidatos = new PanelCandidatos(listaCandidatos,this);
        panelVotos = new PanelVotos();
        panelReportes = new PanelReportes(electionList); // Se pasa la lista de elecciones

        // Crear un TabbedPane para gestionar los paneles
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(240, 240, 240));
        tabbedPane.setForeground(new Color(0, 102, 204));

        // Agregar los paneles al TabbedPane
        tabbedPane.addTab("Elecciones", panelElecciones);
        tabbedPane.addTab("Candidatos", panelCandidatos);
        tabbedPane.addTab("Votos", panelVotos);
        tabbedPane.addTab("Reportes", panelReportes);

        // Agregar el TabbedPane al panel principal
        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);

        // Agregar la barra de menús
        setJMenuBar(crearBarraMenu());
    }

    private JMenuBar crearBarraMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 102, 204));
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JMenu menuGestion = new JMenu("Gestión");
        menuGestion.setFont(new Font("Arial", Font.BOLD, 14));
        menuGestion.setForeground(Color.WHITE);

        JMenuItem itemElecciones = new JMenuItem("Elecciones");
        JMenuItem itemCandidatos = new JMenuItem("Candidatos");
        JMenuItem itemVotos = new JMenuItem("Votos");
        JMenuItem itemReportes = new JMenuItem("Reportes");

        estilizarMenuItem(itemElecciones);
        estilizarMenuItem(itemCandidatos);
        estilizarMenuItem(itemVotos);
        estilizarMenuItem(itemReportes);

        menuGestion.add(itemElecciones);
        menuGestion.add(itemCandidatos);
        menuGestion.add(itemVotos);
        menuGestion.add(itemReportes);

        menuBar.add(menuGestion);

        // Eventos para cambiar entre paneles
        itemElecciones.addActionListener(e -> cambiarPanel(panelElecciones));
        itemCandidatos.addActionListener(e -> cambiarPanel(panelCandidatos));
        itemVotos.addActionListener(e -> cambiarPanel(panelVotos));
        itemReportes.addActionListener(e -> cambiarPanel(panelReportes));

        return menuBar;
    }

    private void estilizarMenuItem(JMenuItem menuItem) {
        menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
        menuItem.setBackground(Color.WHITE);
        menuItem.setForeground(new Color(0, 102, 204));
        menuItem.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void cambiarPanel(JPanel nuevoPanel) {
        panelPrincipal.removeAll();
        panelPrincipal.add(nuevoPanel, BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    public void actualizarPanelElecciones() {
    panelElecciones.actualizarComboCandidatos();
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
