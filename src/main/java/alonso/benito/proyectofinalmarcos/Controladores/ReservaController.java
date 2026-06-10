package alonso.benito.proyectofinalmarcos.Controladores;

import alonso.benito.proyectofinalmarcos.Enums.ReservaMensaje;
import alonso.benito.proyectofinalmarcos.Modelos.Plato;
import alonso.benito.proyectofinalmarcos.Modelos.Reserva;
import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Servicios.ServicioReserva;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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

    // MEJORAR ESTE METODO, esta haciendo mucho
    // y el http session se esta pasando muchas veces, se podria manejar de otra forma, pero por ahora lo dejo asi
    // 9/6/2026 metodo mejorado

    @PostMapping("/guardar")
    public String guardarReserva(HttpSession session, @ModelAttribute Reserva reserva, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        reserva.setUsuario(usuario);

        ReservaMensaje resultado = servicioReserva.guardarReserva(reserva);
        if(resultado == ReservaMensaje.ERROR_SIN_MESAS) {
            model.addAttribute("errorMesas", resultado.getMensaje());
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


    // Nuevo endpoint para actualizar platos de la reserva desde el cliente
    @PostMapping("/actualizarPlatos")
    public String actualizarPlatos(@RequestParam("idReserva") int idReserva,
                                   @RequestParam(name = "platosIds", required = false) List<Integer> platosIds,
                                   Model model) {
        Reserva actualizada = servicioReserva.actualizarPlatosReserva(idReserva, platosIds);
        model.addAttribute("reserva", actualizada);
        model.addAttribute("carta", servicioReserva.obtenerCarta());
        model.addAttribute("tituloCarta", "Carta Completa:");
        model.addAttribute("mensajeGuardado", "Cambios guardados correctamente.");
        return "gestion_reserva";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(HttpSession session, @PathVariable int id, Model model) {

        servicioReserva.cancelarReserva(id);
        model.addAttribute("mensajeCancelacion", "Tu reserva ha sido cancelada exitosamente.");
        return "gestion_reserva";
    }
}
