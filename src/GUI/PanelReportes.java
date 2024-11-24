package GUI;

import Models.Candidato;
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
    private ListaEnlazada<Eleccion> electionList;


    public PanelReportes(ListaEnlazada<Eleccion> electionList) {
        this.elecciones = elecciones;
        this.electionList = electionList;
        setLayout(new BorderLayout(10, 10)); // Mejor separaci�n

        // Panel superior para entrada y bot�n
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(3, 1, 10, 10));
        panelSuperior.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Generar Reportes", 0, 0, new Font("Arial", Font.BOLD, 14), Color.DARK_GRAY));
        panelSuperior.setBackground(new Color(245, 245, 245)); // Fondo claro para el panel superior

        JLabel lblNombreEleccion = new JLabel("Nombre de la Elecci�n:");
        lblNombreEleccion.setFont(new Font("Arial", Font.PLAIN, 12)); // Fuente para la etiqueta

        txtNombreEleccion = new JTextField();
        txtNombreEleccion.setFont(new Font("Arial", Font.PLAIN, 12));
        txtNombreEleccion.setBackground(Color.WHITE);
        txtNombreEleccion.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        btnGenerarReporte = new JButton("Generar Reporte");
        btnGenerarReporte.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerarReporte.setBackground(new Color(0, 123, 255)); // Color de fondo para el bot�n
        btnGenerarReporte.setForeground(Color.WHITE);
        btnGenerarReporte.setFocusPainted(false);
        btnGenerarReporte.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnGenerarReporte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cursor tipo mano

        panelSuperior.add(lblNombreEleccion);
        panelSuperior.add(txtNombreEleccion);
        panelSuperior.add(btnGenerarReporte);

        add(panelSuperior, BorderLayout.NORTH);

        // �rea de texto para mostrar reportes
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

        // Acci�n del bot�n
        configurarEventos();
    }

    private void configurarEventos() {
        btnGenerarReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEleccion = txtNombreEleccion.getText().trim();

                if (nombreEleccion.isEmpty()) {
                    JOptionPane.showMessageDialog(PanelReportes.this, "Por favor, ingrese el nombre de la elecci�n.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Eleccion eleccion = buscarEleccion(nombreEleccion);
                    if (eleccion != null) {
                        generarReporte(nombreEleccion);
                    } else {
                        JOptionPane.showMessageDialog(PanelReportes.this, "Elecci�n no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    limpiarFormulario();
                }
            }
        });
    }

    private Eleccion buscarEleccion(String nombreEleccion) {
        if (elecciones == null || elecciones.getCabeza() == null) {
        JOptionPane.showMessageDialog(this, "No hay elecciones disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
        }

        Nodo<Eleccion> nodo = elecciones.getCabeza();
        while (nodo != null) {
        if (nodo.getData().getNombre().equalsIgnoreCase(nombreEleccion)) {
            return nodo.getData();
        }
            nodo = nodo.getPtr();
        }

        JOptionPane.showMessageDialog(this, "No se encontr� la elecci�n: " + nombreEleccion, "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    private void limpiarFormulario() {
        txtNombreEleccion.setText("");
    }

    private void generarReporte(String nombreEleccion) {
    Eleccion eleccionSeleccionada = null;

    // Buscar la elecci�n por nombre
    for (Nodo<Eleccion> nodo = electionList.getCabeza(); nodo != null; nodo = nodo.getPtr()) {
        if (nodo.getData().getNombre().equalsIgnoreCase(nombreEleccion)) {
            eleccionSeleccionada = nodo.getData();
            break;
        }
    }

    if (eleccionSeleccionada == null) {
        JOptionPane.showMessageDialog(this, "No se encontr� la elecci�n: " + nombreEleccion, "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Calcular votos
    int totalVotos = 0;
    int votosNulos = 0;
    int votosBlancos = 0;
    Candidato ganador = null;
    int maxVotos = 0;

    for (Nodo<Candidato> nodo = eleccionSeleccionada.getCandidatosAsociados().getCabeza(); nodo != null; nodo = nodo.getPtr()) {
        Candidato candidato = nodo.getData();
        totalVotos += candidato.getVotos();
        if (candidato.getVotos() > maxVotos) {
            maxVotos = candidato.getVotos();
            ganador = candidato;
        }
    }

    // Mostrar el reporte
    String reporte = "Nombre Elecci�n: " + eleccionSeleccionada.getNombre() + "\n" +
                     "Total Votos: " + totalVotos + "\n" +
                     "Votos Nulos: " + votosNulos + "\n" +
                     "Votos Blancos: " + votosBlancos + "\n" +
                     "Ganador: " + (ganador != null ? ganador.getNombre() : "Sin ganador") + "\n" +
                     "Fecha Generaci�n: " + java.time.LocalDate.now() + "\n";

    areaReportes.append(reporte + "\n-----------------------------\n");
    }
}
