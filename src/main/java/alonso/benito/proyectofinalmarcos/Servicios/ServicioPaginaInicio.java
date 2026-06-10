package alonso.benito.proyectofinalmarcos.Servicios;

import alonso.benito.proyectofinalmarcos.Modelos.Plato;
import alonso.benito.proyectofinalmarcos.Modelos.ResenaPublicaDTO;
import alonso.benito.proyectofinalmarcos.Repositorios.PlatoRepository;
import alonso.benito.proyectofinalmarcos.Repositorios.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPaginaInicio {
    @Autowired
    PlatoRepository platoRepository;
    @Autowired
    ResenaRepository resenasRepository;

    public List<Plato> obtenerPlatosDestacados() {
        return  platoRepository.findTop6By();
    }

    public List<ResenaPublicaDTO> obtenerResenasDestacados() {
        //Enviar de 3 a 4 resenas destacadas
        return resenasRepository.findTop3ByOrderByCalificacionDesc();
    }
}
