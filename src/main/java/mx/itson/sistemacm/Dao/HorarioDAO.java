/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mx.itson.sistemacm.Db.Conexion;
import mx.itson.sistemacm.Modelos.Horario;

/**
 *
 * @author torre
 */
public class HorarioDAO {
    
    public boolean actualizarEstado(int idHorario, boolean disponible) {
        String sql = "UPDATE horario SET estado = ? WHERE id = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, disponible);
            ps.setInt(2, idHorario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<Horario> obtenerDisponibles() {
        List<Horario> lista = new ArrayList<>();
        String sql = "SELECT * FROM horario WHERE estado = 1";
        try (Connection con = Conexion.obtener();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Horario h = new Horario();
                h.setId(rs.getInt("id"));
                h.setDia(rs.getString("dia"));
                h.setHoraInicio(rs.getString("hora_inicio"));
                h.setHoraFin(rs.getString("hora_fin"));
                h.setEstado(rs.getBoolean("estado"));
                lista.add(h);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    
}
