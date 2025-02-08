package repositorios;

import entidades.Tratamiento;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TratamientoRepositorio implements Repositorio <Tratamiento> {

    private Session session;

    public TratamientoRepositorio(Session session) {
        this.session = session;
    }

    @Override
    public void guardar(Tratamiento tratamiento) {

        Transaction trx = session.beginTransaction();
        session.persist(tratamiento);
        System.out.println("Se ha guardado el tratamiento");
        trx.commit();

    }

    @Override
    public List<Tratamiento> encontrarTodos() {

        Transaction trx = session.beginTransaction();
        List<Tratamiento> tratamientos = session.createQuery("select t from Tratamiento t",Tratamiento.class)
                .getResultList();
        trx.commit();
        return tratamientos;
    }

    @Override
    public Tratamiento encontrarUnoPorId(int id) {

        Transaction trx = session.beginTransaction();
        Tratamiento tratamiento = session.createQuery("select t from Tratamiento t where t.id=:idT",Tratamiento.class)
                .setParameter("idT",id)
                .getSingleResult();
        trx.commit();
        return tratamiento;
    }

    @Override
    public void actualizar(Tratamiento tratamiento) {

        Transaction trx = session.beginTransaction();
        session.update(tratamiento);
        System.out.println("Se ha actualizado el tratamiento");
        trx.commit();

    }

    @Override
    public void eliminar(Tratamiento tratamiento) {

        Transaction trx = session.beginTransaction();
        session.remove(tratamiento);
        System.out.println("Se ha eliminado el tratamiento");
        trx.commit();

    }

    public int obtenerSiguienteId() {
        Transaction trx = session.beginTransaction();
        Integer ultimoId = (Integer) session.createQuery("select max(t.id) from Tratamiento t")
                .getSingleResult();
        trx.commit();

        return ultimoId != null ? ultimoId +1 : 1;
    }
}
