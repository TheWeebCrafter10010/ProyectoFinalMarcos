package alonso.benito.proyectofinalmarcos.Modelos;

public record Plato(int id_plato, String nombre, String descripcion, double precio, int tiempo_preparacion, String imagen, Categoria categoria,boolean estado) {
}
