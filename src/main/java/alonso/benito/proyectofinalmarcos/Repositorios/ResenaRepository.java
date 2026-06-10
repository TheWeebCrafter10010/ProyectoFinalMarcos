package alonso.benito.proyectofinalmarcos.Repositorios;

import alonso.benito.proyectofinalmarcos.Modelos.Resena;
import alonso.benito.proyectofinalmarcos.Modelos.ResenaPublicaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {
    List<ResenaPublicaDTO> findTop3ByOrderByCalificacionDesc();
}
