package alonso.benito.proyectofinalmarcos.Controladores;

import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/perfil")
public class PerfilController {
    private final UsuarioRepository usuarioRepository;

    public PerfilController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public String perfil(Principal principal, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName());
        model.addAttribute("usuario", usuario);
        return "perfil";
    }
}
