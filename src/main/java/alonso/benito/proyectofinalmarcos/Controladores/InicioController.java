package alonso.benito.proyectofinalmarcos.Controladores;

import alonso.benito.proyectofinalmarcos.Servicios.ServicioPaginaInicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class InicioController {


    private final ServicioPaginaInicio servicioPaginaInicio;

    InicioController(ServicioPaginaInicio servicioPaginaInicio) {
        this.servicioPaginaInicio = servicioPaginaInicio;
    }
    @GetMapping
    public String mostrarPaginaInicio(Model modelo) {
        modelo.addAttribute("platos", servicioPaginaInicio.obtenerPlatosDestacados());
        //Con este modelo armar el div de los platos que se muestran en el inicio
        modelo.addAttribute("resenas", servicioPaginaInicio.obtenerResenasDestacados());
        //Con este modelo armar el div de las reseñas que se muestran en el inicio
        return "index";
    }

}
