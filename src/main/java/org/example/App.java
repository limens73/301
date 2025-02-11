package org.example;

import entidades.*;
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
    static RecibeRepositorio recibeRepositorio;

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
                    asignarDoctorPaciente(session);
                    break;
                }

                case 4: {
                    asignarTratamientoPaciente(session);
                    break;
                }

                case 5: {
                    cambiarHospitalTratamiento(session);
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

    private static void cambiarHospitalTratamiento(Session session) {

        entrada = new Scanner(System.in);
        System.out.println("Introduce el nombre del tratamiento");
        String tipoTratamiento = entrada.nextLine();

        List<Tratamiento> tratamientos = session.createQuery("select t from Tratamiento t where t.tipo =: nomT", Tratamiento.class)
                .setParameter("nomT",tipoTratamiento)
                .getResultList();

        Tratamiento tratamiento = null;
        if(!tratamientos.isEmpty()){
            tratamiento = tratamientos.get(0);

        }
        if(tratamiento == null){
            System.out.println("No se encontró un tratamiento con ese nombre.");
        }else{ // Caso en el que continuamos adelante al existir el tratamiento

            int idTratamiento = tratamiento.getId();
            System.out.println("Introduce el nombre del hospital");
            String nombreHospital = entrada.nextLine();

            List<Hospital> hospitales = session.createQuery("select h from Hospital h where h.nombre =: nomH",Hospital.class)
                    .setParameter("nomH",nombreHospital)
                    .getResultList();

            Hospital hospital = null;
            if(!hospitales.isEmpty()){
                hospital = hospitales.get(0);
            }

            if(hospital == null){
                System.out.println("No se encontró un hospital con ese nombre");
            }else{ // Caso válido en el que también existe el hospital y seguimos adelante

                int idHospital = hospital.getId();

                // A continuación pedimos datos del nuevo hospital

                System.out.println("Introduce el nombre del nuevo hospital");
                String nombreNuevoHospital = entrada.nextLine();

                List<Hospital> nuevoshospitales = session.createQuery("select h from Hospital h where h.nombre =: nomH",Hospital.class)
                        .setParameter("nomH",nombreNuevoHospital)
                        .getResultList();

                Hospital nuevoHospital = null;
                if(!nuevoshospitales.isEmpty()){

                    nuevoHospital = nuevoshospitales.get(0);
                }
                if(nuevoHospital == null){
                    System.out.println("No se encontró un hospital con ese nombre");
                }else{ // Caso válido en el que es correcto el tratamiento, el hospital y el nuevo hospital

                    int idNuevoHospital = nuevoHospital.getId();

                    List<Tratamiento> tratamientosModificar = session.createQuery("select t from Tratamiento t where t.id =:idT and t.hospital.id =:idH",Tratamiento.class)
                            .setParameter("idT",idTratamiento).setParameter("idH",idHospital).getResultList();

                    Tratamiento tratamientoModificar = null;
                    if(!tratamientosModificar.isEmpty()){
                        tratamientoModificar = tratamientosModificar.get(0);
                    }

                    if(tratamientoModificar== null){

                        System.out.println("Es posible que el tratamiento no esté asociado al hospital indicado");

                    }else{


                        try {
                            tratamientoModificar.setHospital(nuevoHospital);
                            tratamientoRepositorio = new TratamientoRepositorio(session);
                            tratamientoRepositorio.actualizar(tratamientoModificar);
                        } catch (Exception e) {
                            System.out.println("Ha ocurrido un error y no se ha podido modificar el hospital asignado al tratamiento");
                            throw new RuntimeException(e);

                        }


                    }


                }

            }

        }


    }

    private static void asignarTratamientoPaciente(Session session) {

        entrada = new Scanner(System.in);
        System.out.println("Introduce el nombre del paciente");
        String nombrePaciente = entrada.nextLine();

        List<Paciente> pacientes = session.createQuery("select p from Paciente p where p.nombre =: nomP",Paciente.class)
                .setParameter("nomP",nombrePaciente).getResultList();

        Paciente paciente = null;
        if (!pacientes.isEmpty()){
            paciente = pacientes.get(0);
        }
        if (paciente == null){
            System.out.println("No se encontró un paciente con ese nombre.");
        }else {

            int idPaciente = paciente.getId(); // Este es el caso válido en el que sí existe el paciente con el nombre dado.

            System.out.println("Introduce el nombre del tratamiento");
            String tipoTratamiento = entrada.nextLine();

            List<Tratamiento> tratamientos = session.createQuery("select t from Tratamiento t where t.tipo =: nomT",Tratamiento.class)
                    .setParameter("nomT",tipoTratamiento)
                    .getResultList();

            Tratamiento tratamiento = null;

            if(!tratamientos.isEmpty()){
                tratamiento = tratamientos.get(0);
            }

            if(tratamiento == null){

                System.out.println("No se encontró un tratamiento con ese nombre");
            }else {

               int idTratamiento = tratamiento.getId();
               LocalDate fechaInicio;
               LocalDate fechaFin;
               do{
                   fechaInicio = pedirFecha("Introduce la fecha de inicio de tratamiento con formato dd/mm/aaaa");
                   fechaFin = pedirFecha("Introduce la fecha de fin de tratamiento con formato dd/mm/aaaa");

                   if(fechaFin.isBefore(fechaInicio)){
                       System.out.println("La fecha de inicio no puede ser posterior a la de fin del tratamiento");
                   }

               }while(fechaFin.isBefore(fechaInicio));

                try {

                    RecibePk recibePk = new RecibePk(idPaciente,idTratamiento);
                    Recibe receta = new Recibe(recibePk,paciente,tratamiento,fechaInicio,fechaFin);
                    recibeRepositorio = new RecibeRepositorio(session);
                    recibeRepositorio.guardar(receta);


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }




            }


        }

    }

    private static void asignarDoctorPaciente(Session session) {


        entrada = new Scanner(System.in);

        System.out.println("Introduce el nombre del doctor");
        String nombreDoctor = entrada.nextLine();

        List<Doctor> doctores = session.createQuery("select d from Doctor d where d.nombre =: nomD",Doctor.class)
                .setParameter("nomD",nombreDoctor).getResultList();

        Doctor doctor = null;
        if (!doctores.isEmpty()) {
            doctor = doctores.get(0);
        }

        if (doctor == null) {
            System.out.println("No se encontró un doctor con ese nombre.");
        } else {

            int idDoctor = doctor.getId(); // Este es el caso válido en el que sí existe el doctor con el nombre dado.
            System.out.println("Introduce el nombre del paciente");
            String nombrePaciente = entrada.nextLine();

            List<Paciente> pacientes = session.createQuery("select p from Paciente p where p.nombre =: nomP",Paciente.class)
                    .setParameter("nomP",nombrePaciente).getResultList();

            Paciente paciente = null;
            if (!pacientes.isEmpty()){
                paciente = pacientes.get(0);
            }
            if (paciente == null){
                System.out.println("No se encontró un paciente con ese nombre.");
            }else {

                int idPaciente = paciente.getId(); // Este es el caso válido en el que sí existe el paciente con el nombre dado.

                //Ahora debo comprobar que no existan citas asignadas al doctor

                List<Cita> citas = session.createQuery("select c from Cita c where c.doctor.id =:idD",Cita.class)
                        .setParameter("idD",idDoctor).getResultList();

                if (citas.isEmpty()){ //Este es el caso en que puedo asignar una nueva cita


                    LocalDate fechaCita = pedirFecha("Introduce la fecha de la cita con formato dd/mm/aaaa");
                    System.out.println("Introduce el estado de la cita");
                    String estado = entrada.nextLine();

                    try {

                        citaRepositorio = new CitaRepositorio(session);
                        Cita nuevaCita = new Cita(fechaCita,estado,doctor,paciente);
                        citaRepositorio.guardar(nuevaCita);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }else{
                    System.out.println("No se puede asignar cita. El doctor ya tiene asignada una cita.");
                }

            }

        }

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
                    modificarPaciente(session);
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

    private static void modificarPaciente(Session session) {

        int id = pedirInt("Introduce el id del paciente:");
        Paciente paciente;
        pacienteRepositorio = new PacienteRepositorio(session);

        try {
            paciente = pacienteRepositorio.encontrarUnoPorId(id);

            entrada.nextLine();
            System.out.println("Introduce el nombre del paciente");
            String nombre = entrada.nextLine();
            System.out.println("Introduce la dirección del paciente");
            String direccion = entrada.nextLine();
            LocalDate fechaNacimiento = pedirFecha("Introduce la fecha de nacimiento del paciente con formato dd/mm/aaaa");

            paciente.setNombre(nombre);
            paciente.setDireccion(direccion);
            paciente.setFechaNacimiento(fechaNacimiento);


            pacienteRepositorio.actualizar(paciente);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("No se ha encontrado ningún paciente con ese id.");
        }


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
