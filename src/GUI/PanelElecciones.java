package GUI;

import Models.Candidato;
import Models.Eleccion;
import TDA.ListaEnlazada;
import TDA.Nodo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelElecciones extends JPanel {

    private JTextField txtNombre;
    private JTextField txtFecha;
    private JTextField txtTipo;
    private JComboBox<String> comboCandidatos;
    private DefaultListModel<String> listaCandidatosAsociados;
    private JList<String> listCandidatosAsociados;
    private JButton btnAgregarCandidato;
    private JButton btnCrearEleccion;
    private JTextArea areaElecciones;

    private ListaEnlazada<Eleccion> electionList;
    private ListaEnlazada<Candidato> listaCandidatos;

    public PanelElecciones(ListaEnlazada<Eleccion> electionList, ListaEnlazada<Candidato> listaCandidatos) {
        this.electionList = electionList;
        this.listaCandidatos = listaCandidatos;

        setLayout(new BorderLayout());
        inicializarComponentes();
        configurarEventos();
        actualizarComboCandidatos(); // Carga inicial
    }

    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Crear Elección"));

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JLabel lblFecha = new JLabel("Fecha:");
        txtFecha = new JTextField();

        JLabel lblTipo = new JLabel("Tipo:");
        txtTipo = new JTextField();

        JLabel lblCandidatos = new JLabel("Candidatos Disponibles:");
        comboCandidatos = new JComboBox<>();
        actualizarComboCandidatos();

        btnAgregarCandidato = new JButton("Agregar Candidato");
        listaCandidatosAsociados = new DefaultListModel<>();
        listCandidatosAsociados = new JList<>(listaCandidatosAsociados);

        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblFecha);
        panelFormulario.add(txtFecha);
        panelFormulario.add(lblTipo);
        panelFormulario.add(txtTipo);
        panelFormulario.add(lblCandidatos);
        panelFormulario.add(comboCandidatos);
        panelFormulario.add(btnAgregarCandidato);

        add(panelFormulario, BorderLayout.NORTH);

        // Área para mostrar elecciones creadas
        areaElecciones = new JTextArea();
        areaElecciones.setEditable(false);
        areaElecciones.setBorder(BorderFactory.createTitledBorder("Elecciones Creadas"));
        add(new JScrollPane(areaElecciones), BorderLayout.CENTER);

        // Lista de candidatos asociados
        JPanel panelCandidatosAsociados = new JPanel(new BorderLayout());
        panelCandidatosAsociados.setBorder(BorderFactory.createTitledBorder("Candidatos Asociados"));
        panelCandidatosAsociados.add(new JScrollPane(listCandidatosAsociados), BorderLayout.CENTER);

        btnCrearEleccion = new JButton("Crear Elección");
        panelCandidatosAsociados.add(btnCrearEleccion, BorderLayout.SOUTH);

        add(panelCandidatosAsociados, BorderLayout.EAST);
    }

    private void configurarEventos() {
        // Agregar candidato a la lista de asociados
        btnAgregarCandidato.addActionListener(e -> {
            String candidatoSeleccionado = (String) comboCandidatos.getSelectedItem();
            if (candidatoSeleccionado != null && !listaCandidatosAsociados.contains(candidatoSeleccionado)) {
                listaCandidatosAsociados.addElement(candidatoSeleccionado);
            }
        });

        // Crear elección
        btnCrearEleccion.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String fecha = txtFecha.getText().trim();
            String tipo = txtTipo.getText().trim();

            if (nombre.isEmpty() || fecha.isEmpty() || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Eleccion eleccion = new Eleccion(nombre, fecha, tipo);

            for (int i = 0; i < listaCandidatosAsociados.size(); i++) {
                String candidatoNombre = listaCandidatosAsociados.get(i);
                for (Nodo<Candidato> nodo = listaCandidatos.getCabeza(); nodo != null; nodo = nodo.getPtr()) {
                    if (nodo.getData().getNombre().equals(candidatoNombre)) {
                        eleccion.agregarCandidato(nodo.getData());
                    }
                }
            }

            electionList.agregarFinal(eleccion);

            areaElecciones.append("Nombre: " + nombre + ", Fecha: " + fecha + ", Tipo: " + tipo + "\n");
            listaCandidatosAsociados.clear();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this, "Elección creada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public void actualizarComboCandidatos() {
    comboCandidatos.removeAllItems(); // Limpia el ComboBox
    for (Nodo<Candidato> nodo = listaCandidatos.getCabeza(); nodo != null; nodo = nodo.getPtr()) {
        comboCandidatos.addItem(nodo.getData().getNombre()); // Agrega cada candidato
    }
}


    private void limpiarFormulario() {
        txtNombre.setText("");
        txtFecha.setText("");
        txtTipo.setText("");
    }
}
