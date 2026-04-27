/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Dao;
import java.sql.*;
import mx.itson.sistemacm.Db.Conexion;

/**
 *
 * @author torre
 */
public class CitaDAO {
    
    public boolean finalizarCita(int idCita, int idMedico) {
        
        String sqlMedico = "SELECT id FROM medico WHERE usuario_id = ?";
        String sqlUpdate = "UPDATE cita SET estado = 'Finalizada' WHERE id = ? AND medico_id = ?";

        try (Connection con = Conexion.obtener()) {
            int idRealMedico = 0;
            
            try (PreparedStatement ps1 = con.prepareStatement(sqlMedico)) {
                ps1.setInt(1, idMedico);
                ResultSet rs = ps1.executeQuery();
                if (rs.next()) idRealMedico = rs.getInt("id");
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlUpdate)) {
                ps2.setInt(1, idCita);
                ps2.setInt(2, idRealMedico);
                
                int filas = ps2.executeUpdate();
                return filas > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error en CitaDAO.finalizarCita: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean cancelarCita(int idCita) {
        String sql = "UPDATE cita SET estado = 'Cancelada' WHERE id = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCita);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en CitaDAO.cancelarCita: " + e.getMessage());
            return false;
        }
    }
    
}
