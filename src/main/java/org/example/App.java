package org.example;

import entidades.Doctor;
import entidades.Paciente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repositorios.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    static Scanner entrada;
    static DoctorRepositorio doctorRepositorio;
    static CitaRepositorio citaRepositorio;
    static HospitalRepositorio hospitalRepositorio;
    static PacienteRepositorio pacienteRepositorio;
    static TratamientoRepositorio tratamientoRepositorio;

    public static void main(String[] args) {

        entrada = new Scanner(System.in);
        System.out.println("Gestión hospitalaria");
        Session session = HibernateUtil.get().openSession();
        menuPrincipal(session);


        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }

    private static void menuPrincipal(Session session) {

        int opcion = -1;

        do {

            System.out.println("\n1- Crear, borrar (por id) y modificar los datos de un doctor." +
                    "\n2- Crear, borrar (por nombre) y modificar los datos de un paciente." +
                    "\n3- Asignar un doctor a un paciente." +
                    "\n4- Indicar la fecha de fin del tratamiento de un paciente." +
                    "\n5- Cambiar el hospital de un tratamiento." +
                    "\n6- Mostrar los datos de un Paciente (id, nombre, fecha_nacimiento, direccion, tratamientos que recibe y citas que tiene)." +
                    "\n7- Mostrar los datos de los tratamientos y el hospital en el que se realiza." +
                    "\n8- Mostrar el número total de tratamientos que tiene cada hospital." +
                    "\n9- Salir\n");

            try {
                opcion = entrada.nextInt();

            } catch (Exception e) {
                entrada.nextLine();
                opcion = 0;
                System.out.println("La selección debe ser un número del 1 al 9");


            }

            switch (opcion) {


                case 1: {
                    menuDoctor(session);
                    break;
                }

                case 2: {
                    menuPaciente(session);
                    break;
                }

                case 3: {
                    System.out.println("Opción 3");
                    break;
                }

                case 4: {
                    System.out.println("Opción 4");
                    break;
                }

                case 5: {
                    System.out.println("Opción 5");
                    break;
                }

                case 6: {
                    System.out.println("Opción 6");
                    break;
                }

                case 7: {
                    System.out.println("Opción 7");
                    break;
                }

                case 8: {
                    System.out.println("Opción 8");
                    break;
                }

                case 9: {
                    System.exit(0);
                    break;
                }

                default:
                    System.out.println("Opción no válida. Por favor, elige una opción correcta.");


            }


        } while (true);

    }

    private static void menuPaciente(Session session) {


        int opcion =-1;

        do {

        System.out.println("\n1- Crear los datos de un paciente." +
                    "\n2- Borrar, (por nombre) los datos de un paciente." +
                    "\n3- Modificar los datos de un paciente." +
                    "\n4- Regresar\n");

            try {
                opcion = entrada.nextInt();
            } catch (Exception e) {
                entrada.nextLine();
                opcion = 0;
                System.out.println("La selección debe ser un número del 1 al 4");

            }


            switch (opcion) {

                case 1: {
                    crearPaciente(session);
                    break;
                }
                case 2: {
                    borrarPaciente(session);
                    break;
                }
                case 3: {
                    System.out.println("Opcion 1.3");
                    break;
                }
                case 4: {
                    menuPrincipal(session);
                    break;
                }
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción correcta.");


            }


        } while (opcion != 4);

    }

    private static void borrarPaciente(Session session) {

        entrada.nextLine();
        System.out.println("Introduce el nombre del paciente");
        String nombre = entrada.nextLine();
        Transaction trx = session.beginTransaction();

        try {
            List<Paciente> pacientes = session.createQuery(
                            "select p from Paciente p where p.nombre=:nombreP", Paciente.class)
                    .setParameter("nombreP", nombre)
                    .getResultList();

            if (pacientes.isEmpty()) {
                System.out.println("No se ha encontrado un paciente con ese nombre.");
                return;
            }

            Paciente paciente = pacientes.get(0);

            // Primero elimino las citas con consultas HQL
            session.createQuery("DELETE FROM Cita c WHERE c.paciente = :paciente")
                    .setParameter("paciente", paciente)
                    .executeUpdate();
            // Luego elimino los registros Rebibe en el que se encuentre el cliente
            session.createQuery("DELETE FROM Recibe r WHERE r.paciente = :paciente")
                    .setParameter("paciente", paciente)
                    .executeUpdate();

            // Luego elimino el paciente
            session.remove(paciente);

            trx.commit();
            System.out.println("Paciente eliminado correctamente.");
        } catch (Exception e) {

            System.out.println("Error al eliminar paciente: " + e.getMessage());
        }
    }

    private static void crearPaciente(Session session) {

        entrada.nextLine();
        System.out.println("Introduce el nombre del paciente");
        String nombre = entrada.nextLine();
        System.out.println("Introduce la dirección del paciente");
        String direccion = entrada.nextLine();
        LocalDate fechaNacimiento = pedirFecha("Introduce la fecha de nacimiento del paciente con formato dd/mm/aaaa");
        pacienteRepositorio = new PacienteRepositorio(session);
        int siguienteId = pacienteRepositorio.obtenerSiguienteId(); // Obtengo el último id registrado y le sumo 1
        Paciente paciente = new Paciente(siguienteId,nombre,fechaNacimiento,direccion);
        pacienteRepositorio.guardar(paciente);

    }


    private static void menuDoctor(Session session) {

        int opcion =-1;

        do {
            System.out.println("\n1- Crear los datos de un doctor." +
                    "\n2- Borrar, (por id) los datos de un doctor." +
                    "\n3- Modificar los datos de un doctor." +
                    "\n4- Regresar\n");

            try {
                opcion = entrada.nextInt();
            } catch (Exception e) {
                entrada.nextLine();
                opcion = 0;
                System.out.println("La selección debe ser un número del 1 al 4");

            }


            switch (opcion) {

                case 1: {
                    crearDoctor(session);
                    break;
                }
                case 2: {
                    borrarDoctor(session);
                    break;
                }
                case 3: {
                    modificarDoctor(session);
                    break;
                }
                case 4: {
                    menuPrincipal(session);
                    break;
                }
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción correcta.");


            }


        } while (opcion != 4);

    }

    private static void modificarDoctor(Session session) {


        int id = pedirInt("Introduce el id del doctor:");
        Doctor doctor;
        doctorRepositorio = new DoctorRepositorio(session);

        try {
            doctor = doctorRepositorio.encontrarUnoPorId(id);

            entrada.nextLine();
            System.out.println("Introduce el nombre del doctor");
            String nombre = entrada.nextLine();
            System.out.println("Introduce la especialidad del doctor");
            String especialidad = entrada.nextLine();
            System.out.println("Introduce el teléfono del doctor");
            String telefono = entrada.nextLine();

            doctor.setNombre(nombre);
            doctor.setEspecialidad(especialidad);
            doctor.setTelefono(telefono);


            doctorRepositorio.actualizar(doctor);
        } catch (Exception e) {
            System.out.println("No se ha encontrado ningún doctor con ese id.");
        }


    }

    private static void borrarDoctor(Session session) {

        int id = pedirInt("Introduce el id del doctor:");
        doctorRepositorio = new DoctorRepositorio(session);

        try {
            doctorRepositorio.eliminar(doctorRepositorio.encontrarUnoPorId(id));
        } catch (Exception e) {
            System.out.println("No se ha encontrado ningún doctor con ese id.");
        }

    }

    private static void crearDoctor(Session session) {

        entrada.nextLine();
        System.out.println("Introduce el nombre del doctor:");
        String nombre = entrada.nextLine();
        System.out.println("Introduce la especialidad del doctor:");
        String especialidad = entrada.nextLine();
        System.out.println("Introduce el teléfono del doctor");
        String telefono = entrada.nextLine();
        doctorRepositorio = new DoctorRepositorio(session);
        int siguienteId = doctorRepositorio.obtenerSiguienteId(); // Obtengo el último id registrado y le sumo 1
        Doctor doctor = new Doctor(siguienteId,nombre,especialidad,telefono);
        doctorRepositorio.guardar(doctor);

    }



    private static int pedirInt(String mensaje) {
        System.out.println(mensaje);

        while(!entrada.hasNextInt()){

            System.out.println("El identificador del doctor debe ser un número");
            entrada.next();
        }


        return entrada.nextInt();
    }

    private static LocalDate pedirFecha(String mensaje) {

        entrada = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = null;
        System.out.println(mensaje);

        while(fecha == null){


            String cadena = entrada.nextLine();

            try{
                fecha = LocalDate.parse(cadena,formatter);
            } catch (Exception e) {
                System.out.println("Formato de fecha incorrecto, por favor, introduce la fecha " +
                        "en formato dd/mm/aaaa");
            }

        }

        return fecha;
    }
}
