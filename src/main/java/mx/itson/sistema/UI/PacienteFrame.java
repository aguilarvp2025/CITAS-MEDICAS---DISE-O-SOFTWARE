package mx.itson.sistema.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import mx.itson.sistemacm.Dao.PacienteDAO;
import mx.itson.sistemacm.Modelos.Cita;
import mx.itson.sistemacm.Modelos.Usuario;

public class PacienteFrame extends JFrame {
    private JTable tablaCitas;
    private DefaultTableModel modelo;
    private PacienteDAO pacienteDAO = new PacienteDAO();

    public PacienteFrame(Usuario u) {
        setTitle("Panel de Paciente - " + u.getNombreUsuario());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"ID", "Fecha", "Médico", "Estado"}, 0);
        tablaCitas = new JTable(modelo);
        cargarCitas(u.getId());

        add(new JScrollPane(tablaCitas), BorderLayout.CENTER);

        JButton btnAgendar = new JButton("Agendar Nueva Cita");
        add(btnAgendar, BorderLayout.SOUTH);
    }

    private void cargarCitas(int pacienteId) {
        List<Cita> lista = pacienteDAO.consultarCitas(pacienteId);
        for (Cita c : lista) {
            modelo.addRow(new Object[]{c.getId(), c.getFecha(), c.getNombreMedico(), c.getEstado()});
        }
    }
}