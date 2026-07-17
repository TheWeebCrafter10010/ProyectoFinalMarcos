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
    private final ServicioRegistro servicioRegistro;

    public LoginController(ServicioRegistro servicioRegistro) {
        this.servicioRegistro = servicioRegistro;
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(@RequestParam(name = "error", required = false) String error,
                                         @RequestParam(name = "registro", required = false) String registro,
                                         Model model) {
        if (error != null) model.addAttribute("error", "Correo o contraseña incorrectos.");
        if (registro != null) model.addAttribute("mensaje", "Cuenta creada. Ahora puedes iniciar sesión.");
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
        usuario.setEmail(email.trim().toLowerCase());
        usuario.setPassword(password);

        if (servicioRegistro.registrarUsuario(usuario)) {
            return "redirect:/usuarios/login?registro=true";
        }
        model.addAttribute("error", "El correo ya existe, es inválido o la contraseña tiene menos de 8 caracteres.");
        return "registro";
    }

}
