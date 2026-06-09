package alonso.benito.proyectofinalmarcos.Controladores;

import alonso.benito.proyectofinalmarcos.Modelos.Plato;
import alonso.benito.proyectofinalmarcos.Modelos.Reserva;
import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Servicios.ServicioReserva;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/reservar")
public class ReservaController {

    ServicioReserva servicioReserva;

    public ReservaController(ServicioReserva servicioReserva) {
        this.servicioReserva = servicioReserva;
    }

    @GetMapping
    public String formularioReserva(@SessionAttribute(name = "usuario", required = false) Usuario usuario, Model model) {

        //ESTO ES TEMPORAL, ES PARA QUE UN USUARIO NO PUEDA HACER RESERVAS SI NO ESTA LOGUEADO,
        //SE DEBE ARREGLAR PQ ESTA ES UNA SOLUCION FEA XDD
        if(usuario == null) {
            return "login";
        }
        model.addAttribute("reserva", new Reserva());
        return "form_reserva";
    }

    //MEJORAR ESTE METODO, esta haciendo mucho
    //y el http session se esta pasando muchas veces, se podria manejar de otra forma, pero por ahora lo dejo asi
    @PostMapping("/guardar")
    public String guardarReserva(HttpSession session, @ModelAttribute Reserva reserva, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String resultado = servicioReserva.guardarReserva(usuario,reserva,session);

        if (resultado.equals("ERROR_SIN_MESAS")) {
            model.addAttribute("errorMesas", "Lo sentimos, no hay mesas disponibles para " + reserva.getCantidadPersonas() + " personas en esa fecha y hora. Por favor, elige otro horario.");
            return "form_reserva";
        }

        model.addAttribute("reserva", reserva);
        return "gestion_reserva";
    }


    @GetMapping("/gestion")
    public String paginaGestion() {
        return "gestion_reserva";
    }

    @PostMapping("/consultar")
    public String consultarReserva(@RequestParam("id") int id, Model model) {
        Reserva encontrada =  servicioReserva.buscarPorId(id);
        model.addAttribute("reserva", encontrada);
        model.addAttribute("busquedaRealizada", true);
        return "gestion_reserva";
    }

    @PostMapping("/listarCarta")
    public String listarCarta(@RequestParam("idReserva") int idReserva, Model model) {
        model.addAttribute("reserva",  servicioReserva.buscarPorId(idReserva));
        model.addAttribute("carta",  servicioReserva.obtenerCarta());
        model.addAttribute("tituloCarta", "Carta Completa:");
        return "gestion_reserva";
    }

    @PostMapping("/buscarPlato")
    public String buscarPlato(@RequestParam("idReserva") int idReserva, @RequestParam("termino") String termino, Model model) {
        model.addAttribute("reserva", servicioReserva.buscarPorId(idReserva));
        model.addAttribute("carta", servicioReserva.buscarPlatosPorNombre(termino));
        model.addAttribute("tituloCarta", "Resultados para: '" + termino + "'");
        model.addAttribute("busquedaPlatoRealizada", true);
        return "gestion_reserva";
    }

    @PostMapping("/agregarPlato")
    public String agregarPlato(@RequestParam("idReserva") int idReserva, @RequestParam("idPlato") int idPlato, Model model) {
        Reserva reserva = servicioReserva.buscarPorId(idReserva);
        Plato plato = servicioReserva.buscarPlatoPorId(idPlato);

        if (reserva != null && plato != null) {
            reserva.getPlatosPedidos().add(plato);
        }

        model.addAttribute("reserva", reserva);
        model.addAttribute("carta", servicioReserva.obtenerCarta());
        model.addAttribute("tituloCarta", "Carta Completa:");
        return "gestion_reserva";
    }

    @GetMapping("/eliminarPlato/{idReserva}/{idPlato}")
    public String eliminarPlato(@PathVariable int idReserva, @PathVariable int idPlato, Model model) {
        Reserva reserva = servicioReserva.buscarPorId(idReserva);
        if (reserva != null) {
            Plato platoAEliminar = reserva.getPlatosPedidos().stream()
                    .filter(p -> p.id_plato() == idPlato)
                    .findFirst().orElse(null);
            if (platoAEliminar != null) {
                reserva.getPlatosPedidos().remove(platoAEliminar);
            }
        }

        model.addAttribute("reserva", reserva);
        model.addAttribute("carta", servicioReserva.obtenerCarta());
        model.addAttribute("tituloCarta", "Carta Completa:");
        return "gestion_reserva";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(HttpSession session, @PathVariable int id, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        usuario.reservas().removeIf(reserva -> reserva.getIdReserva() == id);
        servicioReserva.cancelarReserva(id);
        model.addAttribute("mensajeCancelacion", "Tu reserva ha sido cancelada exitosamente.");
        return "gestion_reserva";
    }
}
