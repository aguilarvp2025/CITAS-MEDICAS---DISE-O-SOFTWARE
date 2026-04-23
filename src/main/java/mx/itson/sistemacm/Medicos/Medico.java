/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Medicos;

import java.util.List;
import mx.itson.sistemacm.Citas.Cita;
import mx.itson.sistemacm.Citas.Horario;

/**
 *
 * @author capri
 */
public class Medico {
    
    public int id;
    public String especialidadID;
    public String nombre;
    public Especialidad especialidad; 
    public List<Horario> horarios;   
    public List<Cita> citas; 
    
    public void consultarCita() {
        
    }

    public void atenderPaciente() {
        
    }
    
    
            
    
}
