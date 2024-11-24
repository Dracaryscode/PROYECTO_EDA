package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelVotos extends JPanel {

    private JTextField txtIdMesa;
    private JTextField txtDniVotante;
    private JTextField txtCandidato;
    private JButton btnRegistrarVoto;
    private JButton btnProcesarVotos;
    private JTextArea areaVotos;

    public PanelVotos() {
        setLayout(new BorderLayout());

        // Panel superior para formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registro de Votos"));

        // Campos del formulario
        JLabel lblIdMesa = new JLabel("ID Mesa:");
        txtIdMesa = new JTextField();

        JLabel lblDniVotante = new JLabel("DNI Votante:");
        txtDniVotante = new JTextField();

        JLabel lblCandidato = new JLabel("Candidato:");
        txtCandidato = new JTextField();

        btnRegistrarVoto = new JButton("Registrar Voto");
        btnProcesarVotos = new JButton("Procesar Todos los Votos");

        panelFormulario.add(lblIdMesa);
        panelFormulario.add(txtIdMesa);
        panelFormulario.add(lblDniVotante);
        panelFormulario.add(txtDniVotante);
        panelFormulario.add(lblCandidato);
        panelFormulario.add(txtCandidato);
        panelFormulario.add(btnRegistrarVoto);
        panelFormulario.add(btnProcesarVotos);

        add(panelFormulario, BorderLayout.NORTH);

        // Área de texto para mostrar los votos registrados
        areaVotos = new JTextArea();
        areaVotos.setEditable(false);
        areaVotos.setBorder(BorderFactory.createTitledBorder("Votos Registrados"));
        add(new JScrollPane(areaVotos), BorderLayout.CENTER);

        // Acciones de los botones
        configurarEventos();
    }

    private void configurarEventos() {
        btnRegistrarVoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idMesa = txtIdMesa.getText().trim();
                String dniVotante = txtDniVotante.getText().trim();
                String candidato = txtCandidato.getText().trim();

                if (idMesa.isEmpty() && dniVotante.isEmpty() && candidato.isEmpty()) {
                    // Si todos los campos están vacíos, registrar como voto blanco
                    registrarVoto("Voto Blanco");
                    limpiarFormulario();
                } else if (idMesa.isEmpty() || dniVotante.isEmpty() || candidato.isEmpty()) {
                    // Si solo un campo está vacío, registrar como voto nulo
                    registrarVoto("Voto Nulo");
                    limpiarFormulario();
                } else if (!esDniValido(dniVotante)) {
                    // Si el DNI no es válido, registrar como voto nulo
                    registrarVoto("Voto Nulo");
                    limpiarFormulario();
                } else {
                    // Si todos los campos son correctos, registrar el voto
                    registrarVoto("Voto Registrado: ID Mesa: " + idMesa + ", DNI Votante: " + dniVotante + ", Candidato: " + candidato);
                    limpiarFormulario();
                }
            }
        });

        btnProcesarVotos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarVotos();
            }
        });
    }

    private void limpiarFormulario() {
        txtIdMesa.setText("");
        txtDniVotante.setText("");
        txtCandidato.setText("");
    }

    private void registrarVoto(String voto) {
        areaVotos.append(voto + "\n");
        JOptionPane.showMessageDialog(this, voto, "Voto Registrado", JOptionPane.INFORMATION_MESSAGE);
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

    private void procesarVotos() {
        String[] votos = areaVotos.getText().split("\n");

        if (votos.length == 0 || areaVotos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay votos para procesar.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (String voto : votos) {
            System.out.println("Procesando: " + voto); // Simulación del procesamiento
        }

        areaVotos.setText("");
        JOptionPane.showMessageDialog(this, "Todos los votos han sido procesados.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
