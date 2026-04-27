/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Dao;

import java.sql.*;
import mx.itson.sistemacm.Db.Conexion;
import mx.itson.sistemacm.Modelos.Usuario;

/**
 *
 * @author torre
 */
public class UsuarioDAO {
    
    public Usuario login(String tel, String pass) {
        
        String sql = "SELECT id, telefono, rol FROM usuario WHERE telefono = ? AND password = ?";
        
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, tel);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombreUsuario(rs.getString("telefono"));
                u.setRol(rs.getString("rol"));
                return u;
            }
        } catch (SQLException e) {
            System.err.println("Error en Login: " + e.getMessage());
        }
        return null;
    }

    public boolean registrarPaciente(String tel, String pass, String nombre, int edad) {
        
        String sqlUsuario = "INSERT INTO usuario (password, rol, telefono) VALUES (?, 'PACIENTE', ?)";
        String sqlPaciente = "INSERT INTO paciente (nombre, edad, telefono, usuario_id) VALUES (?, ?, ?, ?)";

        Connection con = null;
        try {
            con = Conexion.obtener();
            con.setAutoCommit(false);

            int idGenerado = 0;
            try (PreparedStatement ps1 = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                ps1.setString(1, pass);
                ps1.setString(2, tel);
                ps1.executeUpdate();
                
                ResultSet rs = ps1.getGeneratedKeys();
                if (rs.next()) idGenerado = rs.getInt(1);
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlPaciente)) {
                ps2.setString(1, nombre);
                ps2.setInt(2, edad);
                ps2.setString(3, tel); 
                ps2.setInt(4, idGenerado);
                ps2.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            System.err.println("Error en Registro: " + e.getMessage());
            return false;
        }
    }
    
    public boolean registrarMedico(String tel, String pass, String nombre, int especialidadId) {
    String sqlUsuario = "INSERT INTO usuario (password, rol, telefono) VALUES (?, 'MEDICO', ?)";
    String sqlMedico = "INSERT INTO medico (nombre, especialidad_id, usuario_id) VALUES (?, ?, ?)";

    Connection con = null;
    try {
        con = Conexion.obtener();
        con.setAutoCommit(false); 

        int idGenerado = 0;
        try (PreparedStatement ps1 = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
            ps1.setString(1, pass);
            ps1.setString(2, tel);
            ps1.executeUpdate();
            
            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) idGenerado = rs.getInt(1);
        }

        try (PreparedStatement ps2 = con.prepareStatement(sqlMedico)) {
            ps2.setString(1, nombre);
            if (especialidadId > 0) {
                ps2.setInt(2, especialidadId);
            } else {
                ps2.setNull(2, java.sql.Types.INTEGER);
            }
            ps2.setInt(3, idGenerado);
            ps2.executeUpdate();
        }

        con.commit();
        return true;
    } catch (SQLException e) {
        if (con != null) { try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
        System.err.println("Error en Registro Médico: " + e.getMessage());
        return false;
    }
    }
    
}
