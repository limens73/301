package repositorios;

import entidades.Recibe;
import org.hibernate.Session;
import entidades.Paciente;
import org.hibernate.Transaction;

import java.util.List;

public class PacienteRepositorio implements Repositorio <Paciente>{

    private Session session;

    public PacienteRepositorio(Session session) {
        this.session = session;
    }

    @Override
    public void guardar(Paciente paciente) {

        Transaction trx = session.beginTransaction();
        session.persist(paciente);
        System.out.println("Se ha guardado el paciente");
        trx.commit();

    }

    @Override
    public List<Paciente> encontrarTodos() {

        Transaction trx = session.beginTransaction();
        List<Paciente> pacientes = session.createQuery("select p from Paciente p",Paciente.class)
                .getResultList();
        trx.commit();
        return pacientes;
    }

    @Override
    public Paciente encontrarUnoPorId(int id) {
        Transaction trx = session.beginTransaction();
        Paciente paciente = session.createQuery("select p from Paciente p where p.id=:idP",Paciente.class)
                .setParameter("idP",id)
                .getSingleResult();
        trx.commit();
        return paciente;
    }

    @Override
    public void actualizar(Paciente paciente) {
        Transaction trx = session.beginTransaction();
        session.update(paciente);
        System.out.println("Se ha actualizado el paciente");
        trx.commit();

    }

    @Override
    public void eliminar(Paciente paciente) {
        Transaction trx = session.beginTransaction();
        session.remove(paciente);
        System.out.println("Se ha eliminado el paciente");
        trx.commit();

    }

    public int obtenerSiguienteId() {
        Transaction trx = session.beginTransaction();
        Integer ultimoId = (Integer) session.createQuery("select max(p.id) from Paciente p")
                .getSingleResult();
        trx.commit();


        return ultimoId != null ? ultimoId +1 : 1;
    }


    public Paciente encontrarIdPorNombre(String nombre) {
        Transaction trx = session.beginTransaction();
        Paciente paciente = session.createQuery("select p from Paciente p where p.nombre=:nombreP",Paciente.class)
                .setParameter("nombreP",nombre)
                .getResultList().get(0);
        trx.commit();
        return paciente;
    }

    public void mostrarDatosPaciente(int id){

        List<Paciente> pacientes = session.createQuery("select p from Paciente p where p.id=:idP",Paciente.class)
                .setParameter("idP",id)
                .getResultList();

        List<Recibe> recetas = session.createQuery("select r from Recibe r where r.paciente.id=:idP",Recibe.class)
                .setParameter("idP",id)
                .getResultList();

        for(Paciente p: pacientes){
            System.out.println(p.toString());
        }

        if(!recetas.isEmpty()){
            for (Recibe r: recetas){
                System.out.println(r.toString());
            }
        }else{

            System.out.println("El paciente no tiene ningún tratamiento en curso");
        }



    }


}
