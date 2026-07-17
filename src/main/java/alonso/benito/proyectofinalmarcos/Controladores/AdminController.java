package alonso.benito.proyectofinalmarcos.Controladores;

import alonso.benito.proyectofinalmarcos.Modelos.Categoria;
import alonso.benito.proyectofinalmarcos.Modelos.Plato;
import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.CategoriaRepository;
import alonso.benito.proyectofinalmarcos.Repositorios.PlatoRepository;
import alonso.benito.proyectofinalmarcos.Repositorios.UsuarioRepository;
import alonso.benito.proyectofinalmarcos.Servicios.ServicioEmail;
import alonso.benito.proyectofinalmarcos.Servicios.ServicioReserva;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ServicioReserva servicioReserva;
    private final UsuarioRepository usuarioRepository;
    private final PlatoRepository platoRepository;
    private final CategoriaRepository categoriaRepository;

    public AdminController(ServicioReserva servicioReserva,
                           UsuarioRepository usuarioRepository,
                           PlatoRepository platoRepository,
                           CategoriaRepository categoriaRepository,
                           ServicioEmail servicioEmail) {
        this.servicioReserva = servicioReserva;
        this.usuarioRepository = usuarioRepository;
        this.platoRepository = platoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("reservas", servicioReserva.obtenerTodas());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("platos", platoRepository.findAll());
        return "admin_dashboard";
    }

    @GetMapping("/eliminarReserva/{id}")
    public String eliminarReservaAdmin(@PathVariable int id) {
        var reserva = servicioReserva.buscarPorId(id);
        servicioReserva.cancelarReserva(id,true);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable int id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        model.addAttribute("usuarioEdit", usuario);
        return "admin_editar_usuario";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@RequestParam("id_usuario") int id,
                                 @RequestParam("nombre") String nombre,
                                 @RequestParam("email") String email,
                                 @RequestParam("rol") String rol) {
        Usuario existente = usuarioRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(nombre);
            existente.setEmail(email);
            existente.setRol(rol);
            usuarioRepository.save(existente);
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/eliminarPlato/{id}")
    public String eliminarPlato(@PathVariable int id) {
        platoRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/nuevoPlato")
    public String nuevoPlato(Model model) {
        model.addAttribute("plato", new Plato());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "admin_nuevo_plato";
    }

    @PostMapping("/guardarPlato")
    public String guardarPlato(@ModelAttribute Plato plato, @RequestParam("idCategoriaSelect") Integer idCategoria) {
        if (idCategoria != null) {
            Categoria cat = categoriaRepository.findById(idCategoria).orElse(null);
            plato.setCategoria(cat);
        }

        // Si es un plato nuevo, asegúrate de setear el estado inicial
        if (plato.getId_plato() == 0) {
            plato.setEstado(true);
        }
        platoRepository.save(plato);
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/editarPlato/{id}")
    public String editarPlato(@PathVariable int id, Model model) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de plato inválido:" + id));

        model.addAttribute( "plato", plato);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "admin_nuevo_plato";
    }
}

