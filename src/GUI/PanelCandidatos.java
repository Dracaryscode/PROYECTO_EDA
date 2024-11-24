package GUI;

import Models.Candidato;
import TDA.ListaEnlazada;
import TDA.Nodo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelCandidatos extends JPanel {

    private JTextField txtNombre;
    private JTextField txtPartido;
    private JTextField txtDni;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JTextArea areaCandidatos;
    private VentanaPrincipal ventanaPrincipal;

    private ListaEnlazada<Candidato> listaCandidatos;

    // Constructor para recibir la lista enlazada
    public PanelCandidatos(ListaEnlazada<Candidato> listaCandidatos, VentanaPrincipal ventanaPrincipal) {
        
        this.ventanaPrincipal = ventanaPrincipal;
        this.listaCandidatos = listaCandidatos; // Asignar la lista enlazada compartida
        setLayout(new BorderLayout());

        // Inicializar la interfaz
        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        // Panel superior para formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Candidatos"));

        // Campos del formulario
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JLabel lblPartido = new JLabel("Partido:");
        txtPartido = new JTextField();

        JLabel lblDni = new JLabel("DNI:");
        txtDni = new JTextField();

        btnAgregar = new JButton("Agregar Candidato");
        btnEliminar = new JButton("Eliminar Candidato");

        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblPartido);
        panelFormulario.add(txtPartido);
        panelFormulario.add(lblDni);
        panelFormulario.add(txtDni);
        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnEliminar);

        add(panelFormulario, BorderLayout.NORTH);

        // Área de texto para mostrar la lista de candidatos
        areaCandidatos = new JTextArea();
        areaCandidatos.setEditable(false);
        areaCandidatos.setBorder(BorderFactory.createTitledBorder("Candidatos Registrados"));
        add(new JScrollPane(areaCandidatos), BorderLayout.CENTER);
    }

    private void configurarEventos() {
        // Evento para agregar candidato
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                String partido = txtPartido.getText().trim();
                String dni = txtDni.getText().trim();

                if (nombre.isEmpty() || partido.isEmpty() || dni.isEmpty()) {
                    JOptionPane.showMessageDialog(PanelCandidatos.this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!esDniValido(dni)) {
                    // Si el DNI no es válido
                    JOptionPane.showMessageDialog(PanelCandidatos.this, "El DNI debe ser numérico y tener 8 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Crear un nuevo objeto Candidato
                    Candidato candidato = new Candidato(nombre, partido, dni);
   
                    // Agregar el candidato a la lista enlazada
                    listaCandidatos.agregarFinal(candidato);

                    // Mostrar el candidato en el área de texto
                    areaCandidatos.append("Nombre: " + nombre + ", Partido: " + partido + ", DNI: " + dni + "\n");

                    // Limpiar los campos de texto
                    limpiarFormulario();
                     JOptionPane.showMessageDialog(PanelCandidatos.this, "Candidato agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Notificar a VentanaPrincipal para actualizar el ComboBox
                    ventanaPrincipal.actualizarPanelElecciones();
                }
            }
        });

        // Evento para eliminar candidato
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(PanelCandidatos.this, "Por favor, ingrese el nombre del candidato a eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Eliminar candidato de la lista
                    eliminarCandidatoDeLista(nombre);
                    limpiarFormulario();
                }
            }
        });
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtPartido.setText("");
        txtDni.setText("");
    }

    private void eliminarCandidatoDeLista(String nombre) {
        boolean encontrado = false;

        // Recorremos la lista enlazada para encontrar y eliminar el candidato
        for (Nodo<Candidato> actual = listaCandidatos.getCabeza(); actual != null; actual = actual.getPtr()) {
            if (actual.getData().getNombre().equalsIgnoreCase(nombre)) {
                listaCandidatos.eliminar(actual.getData());
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            // Actualizar el área de texto para reflejar los cambios
            areaCandidatos.setText("");
            for (Nodo<Candidato> actual = listaCandidatos.getCabeza(); actual != null; actual = actual.getPtr()) {
                Candidato candidato = actual.getData();
                areaCandidatos.append("Nombre: " + candidato.getNombre() + ", Partido: " + candidato.getPartido() + ", DNI: " + candidato.getDni() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Candidato eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Candidato no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean esDniValido(String dni) {
        // Verifica si el DNI tiene el formato correcto (solo números y 8 dígitos)
        try {
            Long.parseLong(dni);  // Intentamos convertir el DNI a número
            return dni.length() == 8;  // Verifica que el DNI tenga 8 caracteres
        } catch (NumberFormatException e) {
            return false;  // Si ocurre un error al convertir, el DNI no es válido
        }
    }
}
