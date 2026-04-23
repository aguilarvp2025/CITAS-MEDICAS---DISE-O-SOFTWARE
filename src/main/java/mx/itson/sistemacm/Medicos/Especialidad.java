/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Medicos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import mx.itson.sistemacm.Db.Conexion;

/**
 *
 * @author capri
 */
public class Especialidad {
    
    public int id;
    public String nombre;
    public String descripcion;
    public List<Medico> medicos;
    
    public void asignar(int medicoId) {
        
        String sql = "UPDATE medico SET especialidad_id = ? WHERE id = ?";

        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, this.id);
            ps.setInt(2, medicoId);

            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Especialidad '" + this.nombre + "' asignada correctamente al médico ID: " + medicoId);
            } else {
                System.out.println("No se encontró el médico con el ID proporcionado.");
            }

        } catch (SQLException e) {
            System.err.println("Error al asignar especialidad: " + e.getMessage());
        }
    }

}
