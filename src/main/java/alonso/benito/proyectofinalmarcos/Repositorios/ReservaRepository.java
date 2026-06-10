package alonso.benito.proyectofinalmarcos.Repositorios;

import alonso.benito.proyectofinalmarcos.Modelos.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}
