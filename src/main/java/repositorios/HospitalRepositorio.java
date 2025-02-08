package repositorios;

import entidades.Hospital;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HospitalRepositorio implements Repositorio <Hospital> {

    private Session session;

    public HospitalRepositorio(Session session) {
        this.session = session;
    }

    @Override
    public void guardar(Hospital hospital) {

        Transaction trx = session.beginTransaction();
        session.persist(hospital);
        System.out.println("Se ha guardado el hospital");
        trx.commit();

    }

    @Override
    public List<Hospital> encontrarTodos() {

        Transaction trx = session.beginTransaction();
        List<Hospital> hospitales = session.createQuery("select h from Hospital h",Hospital.class)
                .getResultList();
        trx.commit();
        return hospitales;
    }

    @Override
    public Hospital encontrarUnoPorId(int id) {

        Transaction trx = session.beginTransaction();
        Hospital hospital = session.createQuery("select h from Hospital h where h.id=:idH",Hospital.class)
                .setParameter("idH",id)
                .getSingleResult();
        trx.commit();
        return hospital;
    }

    @Override
    public void actualizar(Hospital hospital) {

        Transaction trx = session.beginTransaction();
        session.update(hospital);
        System.out.println("Se ha actualizado el hospital");
        trx.commit();

    }

    @Override
    public void eliminar(Hospital hospital) {

        Transaction trx = session.beginTransaction();
        session.remove(hospital);
        System.out.println("Se ha eliminado el hospital");
        trx.commit();

    }

    public int obtenerSiguienteId() {
        Transaction trx = session.beginTransaction();
        Integer ultimoId = (Integer) session.createQuery("select max(h.id) from Hospital h")
                .getSingleResult();
        trx.commit();

        return ultimoId != null ? ultimoId +1 : 1;
    }
}
