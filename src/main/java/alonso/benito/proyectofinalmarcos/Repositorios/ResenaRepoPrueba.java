package alonso.benito.proyectofinalmarcos.Repositorios;

import alonso.benito.proyectofinalmarcos.Modelos.Resena;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("resenaRepoPrueba")
public class ResenaRepoPrueba implements  IResenasRepository {

    @Override
    public List<Resena> getTopResenas(int cantidad) {
        List<Resena> resenas = new ArrayList<>();
        resenas.add(new Resena(1, "Alejandro Ruiz", "Excelente comida y servicio", 5, "2024-06-01"));
        resenas.add(new Resena(2, "Beatriz Sáenz", "Muy buena experiencia, volveré pronto", 4, "2024-06-02"));
        resenas.add(new Resena(3, "Daniela Méndez", "La comida estaba excelente, mejor servicio no pudo haber", 5, "2024-06-03"));
        resenas.add(new Resena(4, "Gabriel Soto", "Ambiente agradable, la comida es lo mejor", 4, "2024-06-04"));
        resenas.add(new Resena(5, "Julieta Peña", "Me encantó todo, especialmente el postre", 5, "2024-06-05"));
        return resenas.subList(0, Math.min(cantidad, resenas.size()));
    }

}
