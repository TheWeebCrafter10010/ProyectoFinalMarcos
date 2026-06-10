package alonso.benito.proyectofinalmarcos.Repositorios;

import alonso.benito.proyectofinalmarcos.Enums.EstadoMesa;
import alonso.benito.proyectofinalmarcos.Modelos.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {

    @Query(value = "SELECT * FROM mesas WHERE capacidad >= :capacidad AND estado = :estado LIMIT 1",
            nativeQuery = true)
    Optional<Mesa> buscarMesaAdecuada(@Param("capacidad") int capacidad,
                                      @Param("estado") String estado);

}
