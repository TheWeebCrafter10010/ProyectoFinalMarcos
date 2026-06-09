package alonso.benito.proyectofinalmarcos.Repositorios;

import alonso.benito.proyectofinalmarcos.Modelos.Plato;

import java.util.List;

public interface IPlatoRepository {
    Plato findById(int id);
    void save(Plato entity);
    void update(Plato entity);
    void delete(int id);
    List<Plato> getAll();
}
