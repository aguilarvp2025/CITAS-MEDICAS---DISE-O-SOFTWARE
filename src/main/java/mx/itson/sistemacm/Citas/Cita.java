/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.sistemacm.Citas;

import java.sql.*;
import java.util.Date;
import mx.itson.sistemacm.Db.Conexion;
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
        String sql = "INSERT INTO cita (fecha, paciente_id, medico_id, horario_id, estado) VALUES (?, ?, ?, ?, 'Programada')";
        
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            
            ps.setTimestamp(1, new Timestamp(this.fecha.getTime()));
            ps.setInt(2, this.paciente.id);
            ps.setInt(3, this.medico.id);
            ps.setInt(4, this.horario.id);
            
            ps.executeUpdate();
            System.out.println("Cita registrada en la base de datos.");
            
        } catch (SQLException e) {
            System.err.println("Error al agendar cita: " + e.getMessage());
        }
    }

    
    public boolean cancelar() {
        String sqlCita = "UPDATE cita SET estado = 'Cancelada' WHERE idCita = ?";
        String sqlHorario = "UPDATE horario SET estado = 1 WHERE id = (SELECT horario_id FROM cita WHERE idCita = ?)";

        try (Connection con = Conexion.obtener()) {
            con.setAutoCommit(false); // Transacción: cancelamos cita y liberamos horario

            PreparedStatement psCita = con.prepareStatement(sqlCita);
            psCita.setInt(1, this.idCita);
            psCita.executeUpdate();

            PreparedStatement psHora = con.prepareStatement(sqlHorario);
            psHora.setInt(1, this.idCita);
            psHora.executeUpdate();

            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al cancelar: " + e.getMessage());
            return false;
        }
    }

    
    public boolean reprogramar() {
        String sql = "UPDATE cita SET fecha = ?, horario_id = ? WHERE idCita = ?";

        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setTimestamp(1, new Timestamp(this.fecha.getTime()));
            ps.setInt(2, this.horario.id);
            ps.setInt(3, this.idCita);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al reprogramar: " + e.getMessage());
            return false;
        }
    }
}