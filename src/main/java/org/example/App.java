package org.example;

import entidades.Doctor;
import org.hibernate.Session;
import repositorios.DoctorRepositorio;

/**
 * Hello world!
 *
 */
public class App 
{
    static DoctorRepositorio doctorRepositorio;

    public static void main(String[] args) {
        System.out.println("Test");

        Session session = HibernateUtil.get().openSession();

        // Desde aquí...

        doctorRepositorio = new DoctorRepositorio(session);
        Doctor doctor = new Doctor(doctorRepositorio.obtenerSiguienteId(), "Pedro","Neurología","649270131");
        doctorRepositorio.guardar(doctor);
        doctor = doctorRepositorio.encontrarUnoPorId(2);
        doctorRepositorio.eliminar(doctor);

        // Hasta aquí, son pruebas

        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }
}
