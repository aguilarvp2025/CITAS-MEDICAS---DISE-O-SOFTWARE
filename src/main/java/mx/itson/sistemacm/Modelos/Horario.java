/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Modelos;

/**
 *
 * @author capri
 */
public class Horario {
    
    private int id;
    private String dia;
    private String horaInicio;
    private String horaFin;
    private boolean estado;

    public Horario() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }
    
    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    
    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }
    
    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
