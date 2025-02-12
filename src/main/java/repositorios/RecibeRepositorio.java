package repositorios;

import entidades.Recibe;
import entidades.RecibePk;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RecibeRepositorio implements Repositorio <Recibe>{

    private Session session;

    public RecibeRepositorio(Session session) {
        this.session = session;
    }

    @Override
    public void guardar(Recibe recibe) {
        Transaction trx = session.beginTransaction();
        session.persist(recibe);
        System.out.println("Se ha guardado la receta");
        trx.commit();

    }

    @Override
    public List<Recibe> encontrarTodos() {
        Transaction trx = session.beginTransaction();
        List<Recibe> recetas = session.createQuery("select r from Recibe r",Recibe.class)
                .getResultList();
        trx.commit();
        return recetas;
    }

    @Override
    public Recibe encontrarUnoPorId(int id) { // Este código no encaja, no funcionará adecuadamente.
        Recibe receta = null;
        return receta;
    }

    public Recibe encontrarUnoPorPk(RecibePk pk){

        Transaction trx = session.beginTransaction();
        Recibe receta = session.createQuery("select r From Recibe r where r.id = IdPk",Recibe.class)
                .setParameter("IdPk",pk)
                .getSingleResult();
        trx.commit();
        return receta;
    }


    @Override
    public void actualizar(Recibe recibe) {

        Transaction trx = session.beginTransaction();
        session.update(recibe);
        System.out.println("Se ha actualizado la receta");
        trx.commit();
    }

    @Override
    public void eliminar(Recibe recibe) {

        Transaction trx = session.beginTransaction();
        session.remove(recibe);
        System.out.println("Se ha eliminado la receta");
        trx.commit();

    }

    public void mostrarRecibePorIdPaciente(int id){


        List<Recibe> recetas = session.createQuery("select r from Recibe r where r.paciente.id=:idP",Recibe.class)
                .setParameter("idP",id)
                .getResultList();

        if(!recetas.isEmpty()){
            for (Recibe r: recetas){
                System.out.println(r.toString());
            }
        }else{

            System.out.println("El paciente no tiene ningún tratamiento en curso");
        }


    }
}
