/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Modelos;


/**
 *
 * @author capri
 */
public class Medico {
    
    private int id;
    private String nombre;
    private String telefono;
    private int especialidadId;
    private int usuarioId;
    
    public Medico() {}
    
    public Medico(int id, String nombre, String telefono, int especialidadId, int usuarioId) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.especialidadId = especialidadId;
        this.usuarioId = usuarioId;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public int getEspecialidadId() { return especialidadId; }
    public void setEspecialidadId(int especialidadId) { this.especialidadId = especialidadId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    

}
