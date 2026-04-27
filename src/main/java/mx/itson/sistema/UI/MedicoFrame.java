package mx.itson.sistema.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import mx.itson.sistemacm.Dao.MedicoDAO;
import mx.itson.sistemacm.Dao.CitaDAO;
import mx.itson.sistemacm.Modelos.Cita;
import mx.itson.sistemacm.Modelos.Usuario;

public class MedicoFrame extends JFrame {
    private JTable tablaAgenda;
    private DefaultTableModel modelo;
    private MedicoDAO medicoDAO = new MedicoDAO();
    private CitaDAO citaDAO = new CitaDAO();
    private Usuario usuarioActual;

    public MedicoFrame(Usuario u) {
        this.usuarioActual = u;
        setTitle("Panel del Médico - Dr. " + u.getNombreUsuario());
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configuración de la tabla de agenda
        modelo = new DefaultTableModel(new Object[]{"ID Cita", "Fecha/Hora", "Paciente", "Estado"}, 0);
        tablaAgenda = new JTable(modelo);
        cargarAgenda();

        add(new JLabel("Mi Agenda de Citas", SwingConstants.CENTER), BorderLayout.NORTH);
        add(new JScrollPane(tablaAgenda), BorderLayout.CENTER);

        // Panel de acciones
        JPanel panelBotones = new JPanel();
        JButton btnAtender = new JButton("Atender (Finalizar Cita)");
        JButton btnRefrescar = new JButton("Actualizar Lista");
        
        panelBotones.add(btnAtender);
        panelBotones.add(btnRefrescar);
        add(panelBotones, BorderLayout.SOUTH);

        // Evento para finalizar la cita seleccionada
        btnAtender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaAgenda.getSelectedRow();
                if (fila != -1) {
                    int idCita = (int) modelo.getValueAt(fila, 0);
                    // Usamos el ID del usuario para validar que es su cita
                    if (citaDAO.finalizarCita(idCita, usuarioActual.getId())) {
                        JOptionPane.showMessageDialog(null, "Cita finalizada con éxito.");
                        cargarAgenda(); // Recargar la tabla
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al procesar la cita.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una cita de la tabla.");
                }
            }
        });

        btnRefrescar.addActionListener(e -> cargarAgenda());
    }

    private void cargarAgenda() {
        modelo.setRowCount(0); // Limpiar tabla
        // El MedicoDAO usa el usuario_id para buscar en la tabla medico y luego sus citas
        List<Cita> lista = medicoDAO.verAgenda(usuarioActual.getId());
        for (Cita c : lista) {
            modelo.addRow(new Object[]{
                c.getId(), 
                c.getFecha(), 
                c.getNombrePaciente(), 
                c.getEstado()
            });
        }
    }
}