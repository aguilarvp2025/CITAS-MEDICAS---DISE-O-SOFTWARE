/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Pacientes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mx.itson.sistemacm.Citas.Cita;
import mx.itson.sistemacm.Db.Conexion;


/**
 *
 * @author capri
 */
public class Paciente {
    
    public int id;
    public int edad;
    public String telefono;
    public String nombre;
    public List<Cita> citas;
    
    public void consultarCita() {
        String sql = "SELECT c.id, c.fecha, c.estado, m.nombre as nombre_medico, p.nombre as nombre_paciente " +
                     "FROM cita c " +
                     "INNER JOIN medico m ON c.medico_id = m.id " +
                     "INNER JOIN paciente p ON c.paciente_id = p.id " +
                     "WHERE p.usuario_id = ?";

        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, this.id);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Mis Citas ---");
            boolean hayCitas = false;
            while (rs.next()) {
                hayCitas = true;
                System.out.println("ID: " + rs.getInt("id") + 
                                   " | Paciente: " + rs.getString("nombre_paciente") +
                                   " | Médico: " + rs.getString("nombre_medico") + 
                                   " | Fecha: " + rs.getString("fecha") + 
                                   " | Estado: " + rs.getString("estado"));
            }
            if (!hayCitas) System.out.println("No tienes citas registradas.");
            
        } catch (SQLException e) {
            System.err.println("Error al consultar citas: " + e.getMessage());
        }
    }
    
    
    public void agendar(int medicoId, int horarioId, String fecha) {
        
        String sqlId = "SELECT id FROM paciente WHERE usuario_id = ?";
        
        
        String sqlCita = "INSERT INTO cita (paciente_id, medico_id, horario_id, fecha, estado) VALUES (?, ?, ?, ?, 'Programada')";

        try (Connection con = Conexion.obtener()) {
            int idRealPaciente = 0;
            try (PreparedStatement ps1 = con.prepareStatement(sqlId)) {
                ps1.setInt(1, this.id);
                ResultSet rs = ps1.executeQuery();
                if (rs.next()) {
                    idRealPaciente = rs.getInt("id");
                }
            }

          
            try (PreparedStatement ps2 = con.prepareStatement(sqlCita)) {
                ps2.setInt(1, idRealPaciente);
                ps2.setInt(2, medicoId);
                ps2.setInt(3, horarioId);
                ps2.setString(4, fecha);
                ps2.executeUpdate();
                System.out.println("¡Cita agendada con éxito para el paciente!");
            }
        } catch (SQLException e) {
            System.err.println("Error al agendar cita: " + e.getMessage());
        }
    }
    
}
