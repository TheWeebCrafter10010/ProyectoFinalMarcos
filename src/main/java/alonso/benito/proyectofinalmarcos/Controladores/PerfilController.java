package alonso.benito.proyectofinalmarcos.Controladores;

import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    UsuarioRepository usuarioRepository;


    @GetMapping
    @Transactional //esto lo ponemos para que cargue las reseñas y reservas del usuario, ya que son relaciones lazy, y sin esto no se cargarian

    public String perfil(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }
        Usuario usuarioConTodo = usuarioRepository.findById(usuario.getId_usuario()).orElse(null);
        model.addAttribute("usuario", usuarioConTodo);
        //Con este modelo construir la vista del perfil, mostrar sus datos y sus reseñas, y agregar un boton para cerrar sesion
        return "perfil";


    }
    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Cierra la sesión actual
        return "redirect:/index"; // Redirige al usuario a la página de inicio después de cerrar sesión
    }
}
