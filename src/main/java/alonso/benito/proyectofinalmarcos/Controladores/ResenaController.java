package alonso.benito.proyectofinalmarcos.Controladores;

import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.UsuarioRepository;
import alonso.benito.proyectofinalmarcos.Servicios.ServicioResena;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/resenas")
public class ResenaController {
    private final ServicioResena servicioResena;
    private final UsuarioRepository usuarioRepository;

    public ResenaController(ServicioResena servicioResena, UsuarioRepository usuarioRepository) {
        this.servicioResena = servicioResena;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam String comentario,
                          @RequestParam int calificacion,
                          Principal principal,
                          RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName());
        try {
            servicioResena.guardar(usuario, comentario, calificacion);
            redirectAttributes.addFlashAttribute("mensajeResena", "Tu reseña fue publicada correctamente.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorResena", ex.getMessage());
        }
        return "redirect:/perfil#resenas";
    }
}
