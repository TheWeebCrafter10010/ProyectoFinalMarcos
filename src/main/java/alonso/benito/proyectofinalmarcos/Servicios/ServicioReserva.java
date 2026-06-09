package alonso.benito.proyectofinalmarcos.Servicios;

import alonso.benito.proyectofinalmarcos.Modelos.Mesa;
import alonso.benito.proyectofinalmarcos.Modelos.Plato;
import alonso.benito.proyectofinalmarcos.Modelos.Reserva;
import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.IBasicRepository;
import alonso.benito.proyectofinalmarcos.Repositorios.IPlatoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioReserva {

    private final IPlatoRepository iPlatoRepository;
    private final IBasicRepository<Usuario, String> usuarioRepo;
    private List<Reserva> listaReservas = new ArrayList<>();

    private List<Mesa> mesasDelLocal = List.of(
            new Mesa(1, 1, 2, "Disponible"),
            new Mesa(2, 2, 2, "Disponible"),
            new Mesa(3, 3, 4, "Disponible"),
            new Mesa(4, 4, 4, "Disponible"),
            new Mesa(5, 5, 6, "Disponible")
    );

    private IPlatoRepository platoRepository;
    public ServicioReserva(@Qualifier("usuarioRepoPrueba") IBasicRepository<Usuario, String> usuarioRepo, IPlatoRepository iPlatoRepository) {
        this.iPlatoRepository = iPlatoRepository;
        this.usuarioRepo = usuarioRepo;
    }
    public String guardarReserva(Usuario usuario, Reserva reserva, HttpSession session) {
        Mesa mesaDisponible = null;

        for (Mesa mesa : mesasDelLocal) {
            if (mesa.capacidad() >= reserva.getCantidadPersonas()) {

                boolean ocupada = listaReservas.stream().anyMatch(r ->
                        r.getFecha().equals(reserva.getFecha()) &&
                                r.getHora().equals(reserva.getHora()) &&
                                r.getMesaAsignada() != null &&
                                r.getMesaAsignada().idMesa() == mesa.idMesa()
                );

                if (!ocupada) {
                    mesaDisponible = mesa;
                    break;
                }
            }
        }

        if (mesaDisponible == null) {
            return "ERROR_SIN_MESAS";
        }

        reserva.setMesaAsignada(mesaDisponible);
        listaReservas.add(reserva);
        usuario.reservas().add(reserva);
        usuarioRepo.update(usuario);

        session.setAttribute("usuario", usuario);
        //aqui se actualiza el usuario en la sesión para reflejar cualquier cambio que se haya hecho en el objeto usuario,
        //como por ejemplo agregar la reserva a su lista de reservas
        return "OK";
    }

    public List<Reserva> obtenerTodas() {
        return listaReservas;
    }

    public Reserva buscarPorId(int id) {
        return listaReservas.stream()
                .filter(r -> r.getIdReserva() == id)
                .findFirst()
                .orElse(null);
    }

    public void cancelarReserva(int id) {
        listaReservas.removeIf(r -> r.getIdReserva() == id);
    }
    public List<Plato> obtenerCarta() {
        return iPlatoRepository.getAll();
    }
    public List<Plato> buscarPlatosPorNombre(String termino) {
        List<Plato> encontrados = new ArrayList<>();
        for (Plato p : obtenerCarta()) {
            if (p.nombre().toLowerCase().contains(termino.toLowerCase())) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }
    public Plato buscarPlatoPorId(int idPlato) {
        return iPlatoRepository.findById(idPlato);
    }
}
