package mx.itson.sistema.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mx.itson.sistemacm.Dao.UsuarioDAO;
import mx.itson.sistemacm.Modelos.Usuario;

public class LoginFrame extends JFrame {
    private JTextField txtTelefono;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public LoginFrame() {
        setTitle("Sistema de Citas Médicas - Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        add(new JLabel("Teléfono:", SwingConstants.CENTER));
        txtTelefono = new JTextField();
        add(txtTelefono);

        add(new JLabel("Password:", SwingConstants.CENTER));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Iniciar Sesión");
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tel = txtTelefono.getText();
                String pass = new String(txtPassword.getPassword());
                
                Usuario u = usuarioDAO.login(tel, pass);
                
                if (u != null) {
                    JOptionPane.showMessageDialog(null, "Bienvenido " + u.getNombreUsuario());
                    abrirMenu(u);
                    dispose(); // Cierra el login
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void abrirMenu(Usuario u) {
        if (u.getRol().equalsIgnoreCase("PACIENTE")) {
            new PacienteFrame(u).setVisible(true);
        } else {
            new MedicoFrame(u).setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}