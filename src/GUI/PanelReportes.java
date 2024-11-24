package GUI;

import Models.Eleccion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import TDA.ListaEnlazada;
import TDA.Nodo;

public class PanelReportes extends JPanel {

    private JTextField txtNombreEleccion;
    private JButton btnGenerarReporte;
    private JTextArea areaReportes;
    private ListaEnlazada<Eleccion> elecciones;

    public PanelReportes(ListaEnlazada<Eleccion> elecciones) {
        this.elecciones = elecciones;
        setLayout(new BorderLayout(10, 10)); // Mejor separación

        // Panel superior para entrada y botón
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(3, 1, 10, 10));
        panelSuperior.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Generar Reportes", 0, 0, new Font("Arial", Font.BOLD, 14), Color.DARK_GRAY));
        panelSuperior.setBackground(new Color(245, 245, 245)); // Fondo claro para el panel superior

        JLabel lblNombreEleccion = new JLabel("Nombre de la Elección:");
        lblNombreEleccion.setFont(new Font("Arial", Font.PLAIN, 12)); // Fuente para la etiqueta

        txtNombreEleccion = new JTextField();
        txtNombreEleccion.setFont(new Font("Arial", Font.PLAIN, 12));
        txtNombreEleccion.setBackground(Color.WHITE);
        txtNombreEleccion.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        btnGenerarReporte = new JButton("Generar Reporte");
        btnGenerarReporte.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerarReporte.setBackground(new Color(0, 123, 255)); // Color de fondo para el botón
        btnGenerarReporte.setForeground(Color.WHITE);
        btnGenerarReporte.setFocusPainted(false);
        btnGenerarReporte.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnGenerarReporte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cursor tipo mano

        panelSuperior.add(lblNombreEleccion);
        panelSuperior.add(txtNombreEleccion);
        panelSuperior.add(btnGenerarReporte);

        add(panelSuperior, BorderLayout.NORTH);

        // Área de texto para mostrar reportes
        areaReportes = new JTextArea();
        areaReportes.setEditable(false);
        areaReportes.setFont(new Font("Courier New", Font.PLAIN, 12)); // Fuente monoespaciada para el reporte
        areaReportes.setBackground(new Color(240, 240, 240));
        areaReportes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Reportes Generados", 0, 0, new Font("Arial", Font.BOLD, 14), Color.DARK_GRAY));
        areaReportes.setLineWrap(true);
        areaReportes.setWrapStyleWord(true); // Para que el texto se ajuste mejor

        JScrollPane scrollPane = new JScrollPane(areaReportes);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(scrollPane, BorderLayout.CENTER);

        // Acción del botón
        configurarEventos();
    }

    private void configurarEventos() {
        btnGenerarReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEleccion = txtNombreEleccion.getText().trim();

                if (nombreEleccion.isEmpty()) {
                    JOptionPane.showMessageDialog(PanelReportes.this, "Por favor, ingrese el nombre de la elección.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Eleccion eleccion = buscarEleccion(nombreEleccion);
                    if (eleccion != null) {
                        generarReporte(eleccion);
                    } else {
                        JOptionPane.showMessageDialog(PanelReportes.this, "Elección no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    limpiarFormulario();
                }
            }
        });
    }

    private Eleccion buscarEleccion(String nombreEleccion) {
        for (Nodo<Eleccion> actual = elecciones.getCabeza(); actual != null; actual = actual.getPtr()) {
            if (actual.getData().getNombre().equalsIgnoreCase(nombreEleccion)) {
                return actual.getData();
            }
        }
        return null;
    }

    private void limpiarFormulario() {
        txtNombreEleccion.setText("");
    }

    private void generarReporte(Eleccion eleccion) {
        String reporte = "Nombre Elección: " + eleccion.getNombre() + "\n" +
                         "Total Votos: " + eleccion.contarVotosTotales() + "\n" +
                         "Votos Nulos: " + eleccion.contarVotosNulos() + "\n" +
                         "Votos Blancos: " + eleccion.contarVotosBlancos() + "\n" +
                         "Ganador: " + (eleccion.obtenerGanador() != null ? eleccion.obtenerGanador().getNombre() : "Sin ganador") + "\n" +
                         "Fecha Generación: " + java.time.LocalDate.now() + "\n";

        areaReportes.append(reporte + "\n-----------------------------\n");
    }
}
