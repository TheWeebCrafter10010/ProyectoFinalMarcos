package alonso.benito.proyectofinalmarcos.Controladores;

import alonso.benito.proyectofinalmarcos.Repositorios.IPlatoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/menu")
@Controller
public class MenuController {

    private final IPlatoRepository platoRepository;

    public MenuController(@Qualifier("platoRepoPrueba") IPlatoRepository platoRepository) {
        this.platoRepository = platoRepository;
    }

    @GetMapping
    public String mostrarMenu(Model modelo) {
        modelo.addAttribute("platos", platoRepository.getAll());
        //Hacer el html de menu y dividir los platos por sus categorias, de momento son 3: entradas, principales y postres,

        return "menu";
    }
}
