/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Citas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import mx.itson.sistemacm.Db.Conexion;
import mx.itson.sistemacm.Medicos.Medico;


/**
 *
 * @author capri
 */
public class Horario {
    
    public int id;
    public String dia;
    public String horaInicio;
    public String horaFin;
    public boolean estado;
    public Medico medico;
    public List<Cita> citas;
    
    public void marcarDisponible() {
        String sql = "UPDATE horario SET estado = 1 WHERE id = ?";
        actualizarEstado(sql);
    }
    
   
    public void marcarNoDisponible() {
        String sql = "UPDATE horario SET estado = 0 WHERE id = ?";
        actualizarEstado(sql);
    }

    
    private void actualizarEstado(String sql) {
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, this.id);
            int filas = ps.executeUpdate();
            
            if (filas > 0) {
                System.out.println("Estado del horario actualizado correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado del horario: " + e.getMessage());
        }
    }
     
}
