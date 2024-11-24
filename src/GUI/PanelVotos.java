package GUI;

import Models.Candidato;
import Models.Eleccion;
import TDA.ListaEnlazada;
import TDA.Nodo;

import javax.swing.*;
import java.awt.*;

public class PanelVotos extends JPanel {

    private JTextField txtIdMesa;
    private JTextField txtDniVotante;
    private JComboBox<String> comboCandidatos;
    private JButton btnRegistrarVoto;
    private JButton btnProcesarVotos;
    private JTextArea areaVotos;

    private ListaEnlazada<Eleccion> elecciones;
    private Eleccion eleccionActiva;

    public PanelVotos(ListaEnlazada<Eleccion> elecciones) {
        this.elecciones = elecciones;
        setLayout(new BorderLayout());

        // Configuración de componentes
        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registro de Votos"));

        JLabel lblIdMesa = new JLabel("ID Mesa:");
        txtIdMesa = new JTextField();

        JLabel lblDniVotante = new JLabel("DNI Votante:");
        txtDniVotante = new JTextField();

        JLabel lblCandidato = new JLabel("Candidato:");
        comboCandidatos = new JComboBox<>();

        btnRegistrarVoto = new JButton("Registrar Voto");
        btnProcesarVotos = new JButton("Procesar Todos los Votos");

        panelFormulario.add(lblIdMesa);
        panelFormulario.add(txtIdMesa);
        panelFormulario.add(lblDniVotante);
        panelFormulario.add(txtDniVotante);
        panelFormulario.add(lblCandidato);
        panelFormulario.add(comboCandidatos);
        panelFormulario.add(btnRegistrarVoto);
        panelFormulario.add(btnProcesarVotos);

        add(panelFormulario, BorderLayout.NORTH);

        areaVotos = new JTextArea();
        areaVotos.setEditable(false);
        areaVotos.setBorder(BorderFactory.createTitledBorder("Votos Registrados"));
        add(new JScrollPane(areaVotos), BorderLayout.CENTER);
    }

    private void configurarEventos() {
        btnRegistrarVoto.addActionListener(e -> {
            String idMesa = txtIdMesa.getText().trim();
            String dniVotante = txtDniVotante.getText().trim();
            String candidatoSeleccionado = (String) comboCandidatos.getSelectedItem();

            if (idMesa.isEmpty() || dniVotante.isEmpty() || candidatoSeleccionado == null || eleccionActiva == null) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos y seleccione una elección.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean votoValido = false;
            for (Nodo<Candidato> nodo = eleccionActiva.getCandidatosAsociados().getCabeza(); nodo != null; nodo = nodo.getPtr()) {
                if (nodo.getData().getNombre().equals(candidatoSeleccionado)) {
                    nodo.getData().incrementarVotos(); // Incrementa los votos del candidato
                    votoValido = true;
                    break;
                }
            }

            if (votoValido) {
                registrarVoto("Voto registrado para: " + candidatoSeleccionado);
            } else {
                registrarVoto("Voto Nulo");
            }

            limpiarFormulario();
        });

        btnProcesarVotos.addActionListener(e -> procesarVotos());
    }

    public void actualizarComboCandidatos(Eleccion eleccion) {
    comboCandidatos.removeAllItems(); // Limpia el ComboBox antes de llenarlo
    if (eleccion != null && eleccion.getCandidatosAsociados() != null) {
        Nodo<Candidato> nodo = eleccion.getCandidatosAsociados().getCabeza();
        while (nodo != null) {
            comboCandidatos.addItem(nodo.getData().getNombre());
            nodo = nodo.getPtr();
        }
    } else {
        comboCandidatos.addItem("No hay candidatos disponibles");
    }
    eleccionActiva = eleccion; // Establece la elección activa
}



    private void registrarVoto(String voto) {
        areaVotos.append(voto + "\n");
        JOptionPane.showMessageDialog(this, voto, "Voto Registrado", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarFormulario() {
        txtIdMesa.setText("");
        txtDniVotante.setText("");
    }

    private void procesarVotos() {
        if (eleccionActiva == null) {
            JOptionPane.showMessageDialog(this, "No hay una elección activa para procesar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder resultados = new StringBuilder("Resultados de la elección: " + eleccionActiva.getNombre() + "\n");

        for (Nodo<Candidato> nodo = eleccionActiva.getCandidatosAsociados().getCabeza(); nodo != null; nodo = nodo.getPtr()) {
            Candidato candidato = nodo.getData();
            resultados.append(candidato.getNombre())
                      .append(": ")
                      .append(candidato.getVotos())
                      .append(" votos\n");
        }

        JOptionPane.showMessageDialog(this, resultados.toString(), "Resultados", JOptionPane.INFORMATION_MESSAGE);
    }
}
