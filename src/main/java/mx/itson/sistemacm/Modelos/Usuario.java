/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Modelos;

/**
 *
 * @author torre
 */
public class Usuario {
    
    private int id;
    private String telefono; 
    private String password; 
    private String rol;
    
    public Usuario() {}
    
    public Usuario(int id, String telefono, String password, String rol) {
        this.id = id;
        this.telefono = telefono;
        this.password = password;
        this.rol = rol;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreUsuario() { return telefono; }
    public void setNombreUsuario(String telefono) { this.telefono = telefono; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    
}
