package alonso.benito.proyectofinalmarcos.Controladores;

import alonso.benito.proyectofinalmarcos.Enums.ReservaMensaje;
import alonso.benito.proyectofinalmarcos.Modelos.Plato;
import alonso.benito.proyectofinalmarcos.Modelos.Reserva;
import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.UsuarioRepository;
import alonso.benito.proyectofinalmarcos.Servicios.ServicioReserva;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
@Controller
@RequestMapping("/reservar")
public class ReservaController {

    private final ServicioReserva servicioReserva;
    private final UsuarioRepository usuarioRepository;

    public ReservaController(ServicioReserva servicioReserva,
                             UsuarioRepository usuarioRepository) {
        this.servicioReserva = servicioReserva;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String formularioReserva(Model model) {
        if (!model.containsAttribute("reserva")) {
            Reserva reserva = new Reserva();
            reserva.setCantidadPersonas(2);
            model.addAttribute("reserva", reserva);
        }
        model.addAttribute("fechaMinima", LocalDate.now());
        return "form_reserva";
    }

    @PostMapping("/guardar")
    public String guardarReserva(@ModelAttribute Reserva reserva,
                                 Principal principal,
                                 Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName());
        reserva.setUsuario(usuario);

        if (reserva.getFecha() == null || reserva.getFecha().isBefore(LocalDate.now())
                || reserva.getHora() == null || reserva.getCantidadPersonas() < 1
                || reserva.getTelefono() == null || reserva.getTelefono().isBlank()) {
            model.addAttribute("errorMesas", "Completa correctamente todos los datos de la reserva.");
            model.addAttribute("fechaMinima", LocalDate.now());
            return "form_reserva";
        }

        ReservaMensaje resultado = servicioReserva.guardarReserva(reserva);
        if (resultado == ReservaMensaje.ERROR_SIN_MESAS) {
            model.addAttribute("errorMesas", resultado.getMensaje());
            model.addAttribute("fechaMinima", LocalDate.now());
            return "form_reserva";
        }

        model.addAttribute("reserva", reserva);
        model.addAttribute("mensajeGuardado", "Reserva creada correctamente.");
        model.addAttribute("correoEnviado",true);
        return "gestion_reserva";
    }

    @GetMapping("/gestion")
    public String paginaGestion() {
        return "gestion_reserva";
    }

    @PostMapping("/consultar")
    public String consultarReserva(@RequestParam("id") int id, Principal principal, Model model) {
        Reserva encontrada = servicioReserva.buscarReservaDelUsuario(id, principal.getName());
        model.addAttribute("reserva", encontrada);
        model.addAttribute("busquedaRealizada", true);
        if (encontrada == null) model.addAttribute("error", "No se encontró una reserva tuya con ese código.");
        return "gestion_reserva";
    }

    @PostMapping("/listarCarta")
    public String listarCarta(@RequestParam("idReserva") int idReserva, Principal principal, Model model) {
        Reserva reserva = servicioReserva.buscarReservaDelUsuario(idReserva, principal.getName());
        if (reserva == null) {
            model.addAttribute("error", "No puedes modificar esa reserva.");
            return "gestion_reserva";
        }
        model.addAttribute("reserva", reserva);
        model.addAttribute("carta", servicioReserva.obtenerCarta());
        model.addAttribute("tituloCarta", "Carta completa");
        return "gestion_reserva";
    }

    @PostMapping("/buscarPlato")
    public String buscarPlato(@RequestParam("idReserva") int idReserva,
                              @RequestParam("termino") String termino,
                              Principal principal,
                              Model model) {
        Reserva reserva = servicioReserva.buscarReservaDelUsuario(idReserva, principal.getName());
        if (reserva == null) {
            model.addAttribute("error", "No puedes modificar esa reserva.");
            return "gestion_reserva";
        }
        model.addAttribute("reserva", reserva);
        model.addAttribute("carta", servicioReserva.buscarPlatosPorNombre(termino));
        model.addAttribute("tituloCarta", "Resultados para: " + termino);
        return "gestion_reserva";
    }

    @PostMapping("/actualizarPlatos")
    public String actualizarPlatos(@RequestParam("idReserva") int idReserva,
                                   @RequestParam(name = "platosIds", required = false) List<Integer> platosIds,
                                   Principal principal,
                                   Model model) {
        Reserva actualizada = servicioReserva.actualizarPlatosReserva(idReserva, platosIds, principal.getName());
        if (actualizada == null) {
            model.addAttribute("error", "No puedes modificar esa reserva.");
            return "gestion_reserva";
        }
        model.addAttribute("reserva", actualizada);
        model.addAttribute("carta", servicioReserva.obtenerCarta());
        model.addAttribute("tituloCarta", "Carta completa");
        model.addAttribute("mensajeGuardado", "Platos actualizados correctamente.");
        return "gestion_reserva";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable int id,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {
        boolean eliminada = servicioReserva.cancelarReserva(id,false);
        redirectAttributes.addFlashAttribute(eliminada ? "mensajeCancelacion" : "error",
                eliminada ? "Tu reserva fue cancelada correctamente." : "No puedes cancelar esa reserva.");
        return "redirect:/reservar/gestion";
    }
}
