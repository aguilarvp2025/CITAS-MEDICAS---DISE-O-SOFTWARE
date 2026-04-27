/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mx.itson.sistemacm.Db.Conexion;
import mx.itson.sistemacm.Modelos.Especialidad;

/**
 *
 * @author torre
 */
public class EspecialidadDAO {
    
    public boolean asignarAMedico(int especialidadId, int medicoId) {
        String sql = "UPDATE medico SET especialidad_id = ? WHERE id = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, especialidadId);
            ps.setInt(2, medicoId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<Especialidad> obtenerTodas() {
        List<Especialidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM especialidad";
        try (Connection con = Conexion.obtener();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Especialidad e = new Especialidad();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setDescripcion(rs.getString("descripcion"));
                lista.add(e);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    
    
}
