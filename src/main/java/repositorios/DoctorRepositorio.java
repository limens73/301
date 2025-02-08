package repositorios;

import entidades.Doctor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DoctorRepositorio implements Repositorio <Doctor>{

    private Session session;

    public DoctorRepositorio(Session session) {
        this.session = session;
    }

    @Override
    public void guardar(Doctor doctor) {

        Transaction trx = session.beginTransaction();
        session.persist(doctor);
        System.out.println("Se ha guardado el doctor");
        trx.commit();


    }

    @Override
    public List<Doctor> encontrarTodos() {
        Transaction trx = session.beginTransaction();
        List<Doctor> doctores = session.createQuery("select d from Doctor d",Doctor.class).getResultList();
        trx.commit();
        return doctores;

    }

    @Override
    public Doctor encontrarUnoPorId(int id) {

        Transaction trx = session.beginTransaction();
        Doctor doctor = session.createQuery("select d from Doctor d where d.id=:idD",Doctor.class)
                .setParameter("idD",id)
                .getSingleResult();
        trx.commit();
        return doctor;

    }

    @Override
    public void actualizar(Doctor doctor) {

        Transaction trx = session.beginTransaction();
        session.update(doctor);
        System.out.println("Se ha actualizado el doctor");
        trx.commit();

    }

    @Override
    public void eliminar(Doctor doctor) {

        Transaction trx = session.beginTransaction();
        session.remove(doctor);
        System.out.println("Se ha eliminado el doctor");
        trx.commit();

    }

    public int obtenerSiguienteId() {
        Transaction trx = session.beginTransaction();
        Integer ultimoId = (Integer) session.createQuery("select max(d.id) from Doctor d")
                .getSingleResult();
        trx.commit();

        return ultimoId != null ? ultimoId +1 : 1;
    }




}
