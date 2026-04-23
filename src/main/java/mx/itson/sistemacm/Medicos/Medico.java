/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Medicos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mx.itson.sistemacm.Citas.Cita;
import mx.itson.sistemacm.Citas.Horario;
import mx.itson.sistemacm.Db.Conexion;
import mx.itson.sistemacm.Pacientes.Usuario;

/**
 *
 * @author capri
 */
public class Medico {
    
    public int id;
    public String especialidadID;
    public String nombre;
    public Especialidad especialidad; 
    public List<Horario> horarios;   
    public List<Cita> citas;
    public Usuario cuenta;
    
    public void consultarCita() {
        
        this.citas = new ArrayList<>();
        
        String sqlMedico = "SELECT id, nombre FROM medico WHERE usuario_id = ?";
        
        //Query para obtener las citas
        String sqlCitas = "SELECT c.id, c.fecha, c.estado, p.nombre as nombre_paciente " +
                     "FROM cita c INNER JOIN paciente p ON c.paciente_id = p.id " +
                     "WHERE c.medico_id = ? AND c.estado = 'Programada' " +
                     "ORDER BY c.fecha ASC";

        try (Connection con = Conexion.obtener()) {
            
           
            try (PreparedStatement ps1 = con.prepareStatement(sqlMedico)) {
                ps1.setInt(1, this.id); 
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    this.id = rs1.getInt("id");
                    this.nombre = rs1.getString("nombre"); 
                }
            }

            
            try (PreparedStatement ps2 = con.prepareStatement(sqlCitas)) {
                ps2.setInt(1, this.id); 
                ResultSet rs2 = ps2.executeQuery();

                System.out.println("--- Agenda del Dr. " + this.nombre + " ---");
                while (rs2.next()) {
                    System.out.println("ID Cita: " + rs2.getInt("id") + 
                                       " | Paciente: " + rs2.getString("nombre_paciente") + 
                                       " | Fecha: " + rs2.getTimestamp("fecha"));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al consultar agenda: " + e.getMessage());
        }
        
    }

    public void atenderPaciente(int idCita) {
        String sql = "UPDATE cita SET estado = 'Completada' WHERE id = ? AND medico_id = ?";

        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCita);
            ps.setInt(2, this.id); 

            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("La cita " + idCita + " ha sido marcada como completada.");
            } else {
                System.out.println("No se encontró la cita o no tienes permiso para modificarla.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al atender paciente: " + e.getMessage());
        }
    }

}
