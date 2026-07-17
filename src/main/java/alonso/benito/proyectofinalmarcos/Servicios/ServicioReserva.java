package alonso.benito.proyectofinalmarcos.Servicios;

import alonso.benito.proyectofinalmarcos.Enums.EstadoMesa;
import alonso.benito.proyectofinalmarcos.Enums.ReservaMensaje;
import alonso.benito.proyectofinalmarcos.Modelos.Mesa;
import alonso.benito.proyectofinalmarcos.Modelos.Plato;
import alonso.benito.proyectofinalmarcos.Modelos.Reserva;
import alonso.benito.proyectofinalmarcos.Repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ServicioEmail servicioEmail;

    @Transactional
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

            String asunto = "Confirmación de tu reserva - Sabor y Tradición";
            String cuerpo = servicioEmail.getConfirmacionReservaCorreo(reserva);
            servicioEmail.enviarEmail(reserva.getUsuario().getEmail(), asunto, cuerpo);
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
    public boolean cancelarReserva(int id,boolean isAdmin) {
        var reservaOpt = reservaRepo.findById(id).orElse(null);
        if (reservaOpt==null) return false;

        var email = reservaOpt.getUsuario().getEmail();
        String mensaje = servicioEmail.getMensajeCancelacionReserva(reservaOpt,isAdmin);

        if (reservaOpt.getUsuario() != null) {
            reservaOpt.getUsuario().getReservas().remove(reservaOpt);
        }
        if (reservaOpt.getMesa() != null) {
            reservaOpt.getMesa().setEstado(EstadoMesa.DISPONIBLE);
            mesaRepo.save(reservaOpt.getMesa());
        }

        reservaOpt.setMesa(null);

        reservaRepo.delete(reservaOpt);

        servicioEmail.enviarEmail(email,"Cancelación de tu reserva - Sabor y Tradición", mensaje);

        return true;
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

    // Nuevo metodo actualiza los platos asociados a una reserva y guarda los cambios
    @Transactional
    public Reserva actualizarPlatosReserva(int idReserva, List<Integer> platosIds, String email) {
        Reserva reserva = buscarReservaDelUsuario(idReserva, email);
        if (reserva == null) return null;

        reserva.getPlatos().clear();
        if (platosIds != null) {
            for (Integer idPlato : platosIds) {
                platoRepo.findById(idPlato).ifPresent(reserva.getPlatos()::add);
            }
        }
        return reservaRepo.save(reserva);
    }

    public Reserva buscarReservaDelUsuario(int id, String email) {
        Reserva reserva = reservaRepo.findById(id).orElse(null);
        if (reserva == null || reserva.getUsuario() == null || !reserva.getUsuario().getEmail().equalsIgnoreCase(email)) {
            return null;
        }
        return reserva;
    }
}
