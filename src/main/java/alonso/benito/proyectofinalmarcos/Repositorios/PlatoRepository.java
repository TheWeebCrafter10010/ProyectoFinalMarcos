package alonso.benito.proyectofinalmarcos.Repositorios;

import alonso.benito.proyectofinalmarcos.Modelos.Plato;
import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {
    List<Plato> findTop6By();
    List<Plato> findByNombreContainingIgnoreCase(String nombre);
}
