package repositorios;

import java.util.List;

public interface Repositorio <T>{

    void guardar(T t);
    List<T> encontrarTodos();
    T encontrarUnoPorId(int id);
    void actualizar(T t);
    void eliminar(T t);
}
