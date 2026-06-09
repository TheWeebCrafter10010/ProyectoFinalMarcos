package alonso.benito.proyectofinalmarcos.Modelos;


import java.util.List;

//Clase de prueba
public record Usuario(int id_usuario, String nombre, String email, String password, List<Resena> resenas,List<Reserva> reservas) {
}
