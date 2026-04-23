/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Citas;

import java.util.Date;
import mx.itson.sistemacm.Medicos.Medico;
import mx.itson.sistemacm.Pacientes.Paciente;

/**
 *
 * @author capri
 */
public class Cita {
    
    public int idCita;
    public String estado;
    public Date fecha;
    public String pacienteID;
    public String medicoID;
    public Paciente paciente; 
    public Medico medico;     
    public Horario horario;
    
    public void agendar() {
        
    }
    
    public boolean cancelar() {
        
        return true; 
    }
    
    public boolean reprogramar() {
        
        return true;
    }
    
    public void verificarDisponibilidad() {
        
    }
}
