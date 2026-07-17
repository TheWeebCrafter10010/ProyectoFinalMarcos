package alonso.benito.proyectofinalmarcos.Servicios;

import alonso.benito.proyectofinalmarcos.Modelos.Resena;
import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.ResenaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ServicioResena {

    private final ResenaRepository resenaRepository;

    public ServicioResena(ResenaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    }

    public Resena guardar(Usuario usuario, String comentario, int calificacion) {
        if (comentario == null || comentario.trim().length() < 5) {
            throw new IllegalArgumentException("La reseña debe tener al menos 5 caracteres.");
        }
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        }

        Resena resena = new Resena();
        resena.setUsuario(usuario);
        resena.setComentario(comentario.trim());
        resena.setCalificacion(calificacion);
        resena.setFecha(LocalDate.now());
        return resenaRepository.save(resena);
    }
}
