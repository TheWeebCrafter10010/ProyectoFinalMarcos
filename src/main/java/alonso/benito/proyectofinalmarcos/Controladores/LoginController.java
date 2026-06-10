package alonso.benito.proyectofinalmarcos.Controladores;

import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Servicios.ServicioRegistro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuarios")
public class LoginController {

    @Autowired
    private ServicioRegistro servicioRegistro;

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }
    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "registro";
    }
    @PostMapping("/guardar")
    public String procesarRegistro(@RequestParam String nombre,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   Model model) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        boolean exito = servicioRegistro.registrarUsuario(usuario);

        if (exito) {

            return "redirect:/usuarios/login";
        } else {
            model.addAttribute("error", "Datos inválidos o el usuario ya existe.");
            //Usar este modelo para mostrar el error en la vista de registro
            return "registro";
        }
    }
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {

        Usuario user = servicioRegistro.loginUsuario(email,password);

        if (user!= null) {
            session.setAttribute("usuario", user);//Guardar el usuario en la sesión para mantenerlo logueado
            //Con este atributo mostrar el nombre del usuario en todas las paginas y
            //Armar el html usuario donde estaran sus datos y sus reseñas
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Credenciales inválidas.");
            //Usar este modelo para mostrar el error en la vista de login
            return "login";
        }
    }

}
