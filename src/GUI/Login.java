package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private int intentosFallidos = 0;

    public Login() {
        setTitle("Login - Sistema de Gestión Electoral");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Estilo adaptable al redimensionar
        setLayout(new BorderLayout());

        // Panel superior con título
        JLabel lblTitulo = new JLabel("Bienvenido", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 102, 204));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel principal del login
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField txtUsuario = new JTextField(15); // Ajuste del tamaño del campo de texto
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField txtPassword = new JPasswordField(15); // Ajuste del tamaño del campo de texto
        JButton btnIngresar = new JButton("Ingresar");

        // Colocación de componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsuario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnIngresar, gbc);

        add(panel, BorderLayout.CENTER);

        // Estilo del botón
        btnIngresar.setBackground(new Color(0, 153, 153));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));

        // Acción del botón ingresar
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                if (usuario.equals("admin") && password.equals("1234")) {
                    dispose(); // Cierra la ventana de login
                    new VentanaPrincipal().setVisible(true); // Abre la ventana principal
                } else {
                    intentosFallidos++;
                    if (intentosFallidos >= 3) {
                        JOptionPane.showMessageDialog(Login.this, "Demasiados intentos fallidos. Cerrando aplicación.", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(0); // Cierra la aplicación
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Tamaño inicial de la ventana
        setSize(500, 400);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}