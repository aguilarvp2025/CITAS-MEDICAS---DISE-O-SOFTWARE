package mx.itson.sistema.UI;

import java.util.Scanner;
import mx.itson.sistemacm.Pacientes.Usuario;
import mx.itson.sistemacm.Pacientes.Paciente;
import mx.itson.sistemacm.Medicos.Medico;

public class Main {

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
                case 2: registrar(sc, "MEDICO"); break;
                case 3: login(sc); break;
                case 4: salir = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void registrar(Scanner sc, String rol) {
        Usuario u = new Usuario();
        System.out.println("\n--- REGISTRO DE " + rol + " ---");
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine();
        System.out.print("Teléfono: ");
        String tel = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        int edad = 0;
        int especialidadId = 0;

      
        if (rol.equals("PACIENTE")) {
            System.out.print("Edad: ");
            edad = sc.nextInt();
            sc.nextLine();
        } else {
            System.out.print("ID de Especialidad: ");
            especialidadId = sc.nextInt();
            sc.nextLine();
        }

      
        if (u.registrar(nombre, tel, pass, rol, especialidadId, edad)) {
            System.out.println("¡Registro exitoso!");
        } else {
            System.out.println("Error al registrar. Verifique los datos o si el teléfono ya existe.");
        }
    }

    private static void login(Scanner sc) {
        Usuario u = new Usuario();
        System.out.print("\nTeléfono: ");
        String tel = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (u.iniciarSesion(tel, pass)) {
            System.out.println("\nLogin correcto. Bienvenido.");
            
            // Validamos contra el nombre exacto de la base de datos (mayúsculas)
            if (u.getRol().equalsIgnoreCase("PACIENTE")) {
                menuPaciente(sc, u);
            } else if (u.getRol().equalsIgnoreCase("MEDICO")) {
                menuMedico(sc, u);
            }
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }

    private static void menuPaciente(Scanner sc, Usuario u) {
        Paciente p = new Paciente();
        p.id = u.getId(); 
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- MENÚ PACIENTE ---");
            System.out.println("1. Agendar Cita");
            System.out.println("2. Ver mis Citas");
            System.out.println("3. Cerrar Sesión");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    System.out.print("Escribe el ID del Médico: ");
                    int idM = sc.nextInt();
                    System.out.print("Escribe el ID del Horario: ");
                    int idH = sc.nextInt();
                    sc.nextLine();
                    p.agendar(idM, idH, "2026-05-15 09:00:00");
                    break;
                case 2:
                    p.consultarCita();
                    break;
                case 3:
                    volver = true;
                    break;
            }
        }
    }

    private static void menuMedico(Scanner sc, Usuario u) {
        Medico m = new Medico();
        m.id = u.getId(); 
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- MENÚ MÉDICO ---");
            System.out.println("1. Ver Agenda");
            System.out.println("2. Atender Paciente (Finalizar Cita)");
            System.out.println("3. Cerrar Sesión");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    m.consultarCita();
                    break;
                case 2:
                    System.out.print("Ingrese el ID de la cita a finalizar: ");
                    int idC = sc.nextInt();
                    sc.nextLine();
                    m.atenderPaciente(idC);
                    break;
                case 3:
                    volver = true;
                    break;
            }
        }
    }
}