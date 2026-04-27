package mx.itson.sistema.UI;

import java.util.Scanner;
import java.util.List;
import mx.itson.sistemacm.Dao.*;
import mx.itson.sistemacm.Modelos.*;

public class Main {
    
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final PacienteDAO pacienteDAO = new PacienteDAO();
    private static final MedicoDAO medicoDAO = new MedicoDAO();
    private static final CitaDAO citaDAO = new CitaDAO();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== SISTEMA DE CITAS MÉDICAS ===");
            System.out.println("1. Registrarse (Paciente)");
            System.out.println("2. Registrarse (Médico)");
            System.out.println("3. Iniciar Sesión");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            if (!sc.hasNextInt()) {
                System.out.println("Por favor, ingrese un número.");
                sc.next();
                continue;
            }

            int op = sc.nextInt();
            sc.nextLine(); 

            switch (op) {
                case 1: registrar(sc, "PACIENTE"); break;
                case 2: registrar(sc, "MEDICO"); break; // Nota: Implementar lógica en UsuarioDAO si es necesario
                case 3: login(sc); break;
                case 4: salir = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void registrar(Scanner sc, String rol) {
    System.out.println("\n--- REGISTRO DE " + rol + " ---");
    System.out.print("Nombre de usuario (Teléfono): ");
    String user = sc.nextLine();
    System.out.print("Password: ");
    String pass = sc.nextLine();
    System.out.print("Nombre completo para el perfil: ");
    String nombreReal = sc.nextLine();

    boolean exito = false;

    if (rol.equals("PACIENTE")) {
        System.out.print("Edad: ");
        int edad = sc.nextInt();
        sc.nextLine();
        exito = usuarioDAO.registrarPaciente(user, pass, nombreReal, edad);
    } else {
        // Mostramos las especialidades que tienes en la base de datos
        System.out.println("Especialidades disponibles:");
        EspecialidadDAO espDAO = new EspecialidadDAO();
        for (Especialidad e : espDAO.obtenerTodas()) {
            System.out.println(e.getId() + ". " + e.getNombre());
        }
        
        System.out.print("Seleccione el ID de su Especialidad: ");
        int espId = sc.nextInt();
        sc.nextLine();
        exito = usuarioDAO.registrarMedico(user, pass, nombreReal, espId);
    }

    if (exito) {
        System.out.println("¡Registro exitoso!");
    } else {
        System.out.println("Error al registrar. Verifique si el teléfono ya está registrado.");
    }
}

    private static void login(Scanner sc) {
        System.out.print("\nUsuario (Teléfono): ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        Usuario u = usuarioDAO.login(user, pass);

        if (u != null) {
            System.out.println("\nLogin correcto. Bienvenido, " + u.getNombreUsuario());
            if (u.getRol().equalsIgnoreCase("Paciente")) {
                menuPaciente(sc, u);
            } else if (u.getRol().equalsIgnoreCase("Medico")) {
                menuMedico(sc, u);
            }
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }

    private static void menuPaciente(Scanner sc, Usuario u) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENU PACIENTE ---");
            System.out.println("1. Agendar Cita");
            System.out.println("2. Ver mis Citas");
            System.out.println("3. Cerrar Sesion");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    System.out.print("ID del Médico: ");
                    int idM = sc.nextInt();
                    System.out.print("ID del Horario: ");
                    int idH = sc.nextInt();
                    sc.nextLine();
                    // Usamos la fecha actual o pedimos una
                    if (pacienteDAO.agendar(u.getId(), idM, idH, "2026-05-15")) {
                        System.out.println("Cita agendada correctamente.");
                    }
                    break;
                case 2:
                    List<Cita> citas = pacienteDAO.consultarCitas(u.getId());
                    for (Cita c : citas) {
                        System.out.println("ID: " + c.getId() + " | Fecha: " + c.getFecha() + 
                                         " | Médico: " + c.getNombreMedico() + " | Estado: " + c.getEstado());
                    }
                    break;
                case 3: volver = true; break;
            }
        }
    }

    private static void menuMedico(Scanner sc, Usuario u) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENU MEDICO ---");
            System.out.println("1. Ver Agenda");
            System.out.println("2. Atender Paciente (Finalizar Cita)");
            System.out.println("3. Cerrar Sesión");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    List<Cita> agenda = medicoDAO.verAgenda(u.getId());
                    for (Cita c : agenda) {
                        System.out.println("ID: " + c.getId() + " | Fecha: " + c.getFecha() + 
                                         " | Paciente: " + c.getNombrePaciente());
                    }
                    break;
                case 2:
                    System.out.print("Ingrese el ID de la cita a finalizar: ");
                    int idC = sc.nextInt();
                    sc.nextLine();
                    if (citaDAO.finalizarCita(idC, u.getId())) {
                        System.out.println("Cita finalizada con éxito.");
                    } else {
                        System.out.println("Error al finalizar la cita.");
                    }
                    break;
                case 3: volver = true; break;
            }
        }
    }
}