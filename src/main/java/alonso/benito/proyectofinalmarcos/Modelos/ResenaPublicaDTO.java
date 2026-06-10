package alonso.benito.proyectofinalmarcos.Modelos;

import java.time.LocalDate;

public interface ResenaPublicaDTO {
    String getComentario();
    int getCalificacion();
    LocalDate getFecha();
    String getUsuarioNombre();
}
