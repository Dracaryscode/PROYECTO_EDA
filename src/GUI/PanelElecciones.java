package GUI;

import Models.Candidato;
import Models.Eleccion;
import TDA.ListaEnlazada;
import TDA.Nodo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
        actualizarComboCandidatos();
    }

    private void inicializarComponentes() {
        // Panel del formulario para crear elecciones
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Crear Elección"));

        // Etiquetas y campos de texto
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JLabel lblFecha = new JLabel("Fecha:");
        txtFecha = new JTextField();

        JLabel lblTipo = new JLabel("Tipo:");
        txtTipo = new JTextField();

        JLabel lblCandidatos = new JLabel("Candidatos Disponibles:");
        comboCandidatos = new JComboBox<>();

        btnAgregarCandidato = new JButton("Agregar Candidato");

        // Añadir componentes al formulario
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

        // Panel para candidatos asociados
        listaCandidatosAsociados = new DefaultListModel<>();
        listCandidatosAsociados = new JList<>(listaCandidatosAsociados);

        JPanel panelCandidatosAsociados = new JPanel(new BorderLayout());
        panelCandidatosAsociados.setBorder(BorderFactory.createTitledBorder("Candidatos Asociados"));
        panelCandidatosAsociados.add(new JScrollPane(listCandidatosAsociados), BorderLayout.CENTER);

        btnCrearEleccion = new JButton("Crear Elección");
        panelCandidatosAsociados.add(btnCrearEleccion, BorderLayout.SOUTH);

        add(panelCandidatosAsociados, BorderLayout.EAST);
    }

    private void configurarEventos() {
    // Evento para agregar candidato a la lista de asociados
    btnAgregarCandidato.addActionListener(e -> {
        String candidatoSeleccionado = (String) comboCandidatos.getSelectedItem();
        if (candidatoSeleccionado != null && !listaCandidatosAsociados.contains(candidatoSeleccionado)) {
            listaCandidatosAsociados.addElement(candidatoSeleccionado);
        }
    });

    // Evento para crear elección
    btnCrearEleccion.addActionListener(e -> {
        // Validar campos obligatorios
        String nombre = txtNombre.getText().trim();
        String fecha = txtFecha.getText().trim();
        String tipo = txtTipo.getText().trim();

        if (nombre.isEmpty() || fecha.isEmpty() || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear nueva elección
        Eleccion eleccion = new Eleccion(nombre, fecha, tipo);

        // Asociar los candidatos seleccionados a la elección
        for (int i = 0; i < listaCandidatosAsociados.size(); i++) {
            String candidatoNombre = listaCandidatosAsociados.get(i);
            Nodo<Candidato> nodo = listaCandidatos.getCabeza();
            while (nodo != null) {
                if (nodo.getData().getNombre().equals(candidatoNombre)) {
                    eleccion.agregarCandidato(nodo.getData());
                    break;
                }
                nodo = nodo.getPtr();
            }
        }

        // Agregar la elección a la lista general
        electionList.agregarFinal(eleccion);

        // Mostrar detalles de la elección en el área de texto
        areaElecciones.append("Nombre: " + nombre + ", Fecha: " + fecha + ", Tipo: " + tipo + ", Candidatos: ");
        Nodo<Candidato> nodo = eleccion.getCandidatosAsociados().getCabeza();
        while (nodo != null) {
            areaElecciones.append(nodo.getData().getNombre() + " ");
            nodo = nodo.getPtr();
        }
        areaElecciones.append("\n");

        // Limpiar el formulario y la lista de candidatos asociados
        listaCandidatosAsociados.clear();
        limpiarFormulario();

        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(this, "Elección creada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    });
}


    public void actualizarComboCandidatos() {
        comboCandidatos.removeAllItems(); // Limpia el ComboBox
        for (Nodo<Candidato> nodo = listaCandidatos.getCabeza(); nodo != null; nodo = nodo.getPtr()) {
            comboCandidatos.addItem(nodo.getData().getNombre()); // Agrega los nombres de los candidatos
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtFecha.setText("");
        txtTipo.setText("");
    }
}
