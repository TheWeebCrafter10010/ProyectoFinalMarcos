package alonso.benito.proyectofinalmarcos.Repositorios;

public interface IBasicRepository <T,ID> {
    T findById(ID id);
    void save(T entity);
    void update(T entity);
    void delete(ID id);
}
