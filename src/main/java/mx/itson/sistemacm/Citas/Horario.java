/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Citas;

import java.util.List;
import mx.itson.sistemacm.Medicos.Medico;

/**
 *
 * @author capri
 */
public class Horario {
    
    public String dia;
    public String horaInicio;
    public String horaFin;
    public boolean estado;
    public Medico medico;
    public List<Cita> citas;
    
    public void marcarDisponible() {

    }
    
    public void marcarNoDisponible() {
        
    }
    
    
}
