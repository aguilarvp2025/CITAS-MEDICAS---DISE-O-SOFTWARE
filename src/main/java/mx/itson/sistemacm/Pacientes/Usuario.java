/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Pacientes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.itson.sistemacm.Db.Conexion;

/**
 *
 * @author torre
 */
public class Usuario {
    
    public int id;
    public String telefono;
    public String password;
    public String rol;
    
    public boolean registrar(String nombre, String telefono, String password, String rol, int especialidadId, int edad) {
    
    String sqlUsuario = "INSERT INTO usuario (telefono, password, rol) VALUES (?, ?, ?)";
    
    String sqlPerfil = (rol.equalsIgnoreCase("PACIENTE"))
            ? "INSERT INTO paciente (nombre, telefono, edad, usuario_id) VALUES (?, ?, ?, ?)"
            : "INSERT INTO medico (nombre, usuario_id, especialidad_id) VALUES (?, ?, ?)";

    try (Connection con = Conexion.obtener()) {
        con.setAutoCommit(false); 

        PreparedStatement psUser = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
        psUser.setString(1, telefono);
        psUser.setString(2, password);
        psUser.setString(3, rol.toUpperCase());
        psUser.executeUpdate();

        ResultSet rs = psUser.getGeneratedKeys();
        int idGenerado = 0;
        if (rs.next()) { idGenerado = rs.getInt(1); }

        PreparedStatement psPerfil = con.prepareStatement(sqlPerfil);
        psPerfil.setString(1, nombre);
        
        if (rol.equalsIgnoreCase("PACIENTE")) {
            psPerfil.setString(2, telefono);
            psPerfil.setInt(3, edad);       
            psPerfil.setInt(4, idGenerado); 
        } else {
            psPerfil.setInt(2, idGenerado);
            psPerfil.setInt(3, especialidadId);
        }
        
        psPerfil.executeUpdate();
        con.commit(); 
        return true;

    } catch (SQLException e) {
        System.err.println("Error en el registro: " + e.getMessage());
        return false;
    }
}
    
    public boolean iniciarSesion(String telefono, String password) {
        String sql = "SELECT id, telefono, rol FROM usuario WHERE telefono = ? AND password = ?";

        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, telefono);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    this.id = rs.getInt("id");
                    this.telefono = rs.getString("telefono");
                    this.rol = rs.getString("rol");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en el login: " + e.getMessage());
        }
        return false;
    }

    public int getId() { return id; }
    public String getRol() { return rol; }
    public String getTelefono() { return telefono; }
    
}
