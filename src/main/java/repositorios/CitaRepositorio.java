package repositorios;

import entidades.Cita;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CitaRepositorio implements Repositorio <Cita>{

    private Session session;

    public CitaRepositorio(Session session) {
        this.session = session;
    }

    @Override
    public void guardar(Cita cita) {

        Transaction trx = session.beginTransaction();
        session.persist(cita);
        System.out.println("Se ha guardado la cita");
        trx.commit();

    }

    @Override
    public List<Cita> encontrarTodos() {

        Transaction trx = session.beginTransaction();
        List<Cita> citas = session.createQuery("select c from Citas c",Cita.class)
                .getResultList();
        trx.commit();
        return citas;

    }

    @Override
    public Cita encontrarUnoPorId(int id) {

        Transaction trx = session.beginTransaction();
        Cita cita = session.createQuery("select c from Cita c where c.id=:idC",Cita.class)
                .setParameter("idC",id)
                .getSingleResult();
        trx.commit();
        return cita;

    }

    @Override
    public void actualizar(Cita cita) {

        Transaction trx = session.beginTransaction();
        session.update(cita);
        System.out.println("Se ha actualizado la cita");
        trx.commit();

    }

    @Override
    public void eliminar(Cita cita) {

        Transaction trx = session.beginTransaction();
        session.remove(cita);
        System.out.println("Se ha eliminado la cita");
        trx.commit();

    }

    public void mostrarCitasPorIdPaciente(int id){

        List<Cita> citasPaciente = session.createQuery("select c from Cita c where c.paciente.id =:idP",Cita.class)
                .setParameter("idP",id)
                .getResultList();

        for(Cita c:  citasPaciente){

            System.out.println(c.toString());
        }
    }

}
