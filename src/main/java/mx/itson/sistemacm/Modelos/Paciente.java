/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Modelos;

/**
 *
 * @author capri
 */
public class Paciente {
    
    private int id;
    private String nombre;
    private String telefono;
    private int edad;
    private int usuarioId;
    
    public Paciente(int id, String nombre, int edad, int usuarioId) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.usuarioId = usuarioId;
    }
    
    public Paciente() {}

    
    public Paciente(int id, String nombre, String telefono, int edad, int usuarioId) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
        this.usuarioId = usuarioId;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
              
}
