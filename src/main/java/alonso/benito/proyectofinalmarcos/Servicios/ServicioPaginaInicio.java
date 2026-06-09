package alonso.benito.proyectofinalmarcos.Servicios;

import alonso.benito.proyectofinalmarcos.Modelos.Plato;
import alonso.benito.proyectofinalmarcos.Modelos.Resena;
import alonso.benito.proyectofinalmarcos.Repositorios.IPlatoRepository;
import alonso.benito.proyectofinalmarcos.Repositorios.IResenasRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioPaginaInicio {
    private final IPlatoRepository platoRepository;
    private final IResenasRepository resenasRepository;

    public ServicioPaginaInicio(@Qualifier("platoRepoPrueba") IPlatoRepository platoRepository, @Qualifier("resenaRepoPrueba") IResenasRepository resenasRepository) {
        this.platoRepository = platoRepository;
        this.resenasRepository = resenasRepository;
    }

    public List<Plato> obtenerPlatosDestacados() {
        //Enviar de 3 a 4 platos destacados
        List<Plato> platosDestacados = new ArrayList<>();
        platosDestacados.add(platoRepository.getAll().get(0));
        platosDestacados.add(platoRepository.getAll().get(2));
        platosDestacados.add(platoRepository.getAll().get(6));
        platosDestacados.add(platoRepository.getAll().get(7));
        platosDestacados.add(platoRepository.getAll().get(1));
        platosDestacados.add(platoRepository.getAll().get(3));
        return platosDestacados;
    }

    public List<Resena> obtenerResenasDestacados() {
        //Enviar de 3 a 4 resenas destacadas
        return resenasRepository.getTopResenas(4);
    }
}
