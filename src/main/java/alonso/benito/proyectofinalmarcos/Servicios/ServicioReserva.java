package alonso.benito.proyectofinalmarcos.Servicios;

import alonso.benito.proyectofinalmarcos.Enums.EstadoMesa;
import alonso.benito.proyectofinalmarcos.Enums.ReservaMensaje;
import alonso.benito.proyectofinalmarcos.Modelos.Mesa;
import alonso.benito.proyectofinalmarcos.Modelos.Plato;
import alonso.benito.proyectofinalmarcos.Modelos.Reserva;
import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.BeanRegistrarDslMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioReserva {
    @Autowired
    PlatoRepository platoRepo;
    @Autowired
    UsuarioRepository usuarioRepo;

    @Autowired
    MesaRepository mesaRepo;

    @Autowired
    ReservaRepository reservaRepo;

    public ReservaMensaje guardarReserva(Reserva reserva) {
        //se puede mejorar para dar diferentes mensajes
        //de momento esta asi
        int cantidadPersonas = reserva.getCantidadPersonas();
        var mesa = mesaRepo.buscarMesaAdecuada(cantidadPersonas, EstadoMesa.DISPONIBLE.name());

        if(mesa.isPresent()){
            Mesa mesaDisponible = mesa.get();
            mesaDisponible.setEstado(EstadoMesa.RESERVADA);
            mesaRepo.save(mesaDisponible);
            reserva.setMesa(mesaDisponible);
            reservaRepo.save(reserva);
        }else {
            return ReservaMensaje.ERROR_SIN_MESAS;
        }
        return ReservaMensaje.RESERVA_EXITOSA;
    }

    public List<Reserva> obtenerTodas() {
        return reservaRepo.findAll();
    }

    public Reserva buscarPorId(int id) {
        return reservaRepo.findById(id).orElse(null);
    }
    @Transactional
    public void cancelarReserva(int id) {
        var reservaOpt = reservaRepo.findById(id).orElse(null);
        if (reservaOpt==null) return;

        if (reservaOpt.getUsuario() != null) {
            reservaOpt.getUsuario().getReservas().remove(reservaOpt);
        }


        reservaOpt.getMesa().setEstado(EstadoMesa.DISPONIBLE);
        mesaRepo.save(reservaOpt.getMesa());
        reservaOpt.setMesa(null);

        reservaRepo.delete(reservaOpt);
    }

    public List<Plato> obtenerCarta() {
        return platoRepo.findAll();
    }

    public List<Plato> buscarPlatosPorNombre(String termino) {
        return  platoRepo.findByNombreContainingIgnoreCase(termino);
    }
    public Plato buscarPlatoPorId(int idPlato) {
        return  platoRepo.findById(idPlato).orElse(null);
    }

    // Nuevo método actualiza los platos asociados a una reserva y guarda los cambios
    public Reserva actualizarPlatosReserva(int idReserva, List<Integer> platosIds) {
        var posible = reservaRepo.findById(idReserva);
        if (posible.isEmpty()) return null;
        Reserva reserva = posible.get();
        // limpiar platos actuales
        reserva.getPlatos().clear();
        if (platosIds != null) {
            for (Integer idPlato : platosIds) {
                platoRepo.findById(idPlato).ifPresent(reserva.getPlatos()::add);
            }
        }
        return reservaRepo.save(reserva);
    }
}
