/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mx.itson.sistemacm.Db.Conexion;
import mx.itson.sistemacm.Modelos.Cita;


/**
 *
 * @author torre
 */
public class PacienteDAO {
    
    public boolean agendar(int usuarioId, int medicoId, int horarioId, String fecha) {
        String sqlId = "SELECT id FROM paciente WHERE usuario_id = ?";
        String sqlCita = "INSERT INTO cita (paciente_id, medico_id, horario_id, fecha, estado) VALUES (?, ?, ?, ?, 'Programada')";

        try (Connection con = Conexion.obtener()) {
            int idRealPaciente = 0;
            
            try (PreparedStatement ps1 = con.prepareStatement(sqlId)) {
                ps1.setInt(1, usuarioId);
                ResultSet rs = ps1.executeQuery();
                if (rs.next()) idRealPaciente = rs.getInt("id");
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlCita)) {
                ps2.setInt(1, idRealPaciente);
                ps2.setInt(2, medicoId);
                ps2.setInt(3, horarioId);
                ps2.setString(4, fecha);
                return ps2.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error en PacienteDAO.agendar: " + e.getMessage());
            return false;
        }
    }
    
    public List<Cita> consultarCitas(int usuarioId) {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.fecha, c.estado, m.nombre as nombre_medico, p.nombre as nombre_paciente " +
                     "FROM cita c " +
                     "INNER JOIN medico m ON c.medico_id = m.id " +
                     "INNER JOIN paciente p ON c.paciente_id = p.id " +
                     "WHERE p.usuario_id = ?";

        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setFecha(rs.getString("fecha"));
                cita.setEstado(rs.getString("estado"));
                cita.setNombreMedico(rs.getString("nombre_medico"));
                cita.setNombrePaciente(rs.getString("nombre_paciente"));
                lista.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error en PacienteDAO.consultarCitas: " + e.getMessage());
        }
        return lista;
    }
    
}
