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
import mx.itson.sistemacm.Modelos.Medico;


/**
 *
 * @author torre
 */
public class MedicoDAO {
    
    public List<Cita> verAgenda(int usuarioId) {
        List<Cita> agenda = new ArrayList<>();
        
        String sql = "SELECT c.id, c.fecha, c.estado, p.nombre as nombre_paciente " +
                     "FROM cita c " +
                     "INNER JOIN paciente p ON c.paciente_id = p.id " +
                     "INNER JOIN medico m ON c.medico_id = m.id " +
                     "WHERE m.usuario_id = ? AND c.estado = 'Programada'";

        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setFecha(rs.getString("fecha"));
                cita.setEstado(rs.getString("estado"));
                cita.setNombrePaciente(rs.getString("nombre_paciente"));
                agenda.add(cita);
            }
            
        } catch (SQLException e) {
            System.err.println("Error en MedicoDAO.verAgenda: " + e.getMessage());
        }
        return agenda;
    }
    
    public List<Medico> obtenerTodos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT id, nombre, especialidad_id FROM medico";
        
        try (Connection con = Conexion.obtener();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));
                m.setEspecialidadId(rs.getInt("especialidad_id"));
                medicos.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener médicos: " + e.getMessage());
        }
        return medicos;
    }
}
