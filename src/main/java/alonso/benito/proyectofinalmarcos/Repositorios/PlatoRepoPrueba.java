package alonso.benito.proyectofinalmarcos.Repositorios;

import alonso.benito.proyectofinalmarcos.Modelos.Categoria;
import alonso.benito.proyectofinalmarcos.Modelos.Plato;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Component("platoRepoPrueba")
public class PlatoRepoPrueba implements IPlatoRepository {

    private List<Plato> listaPlatos;
    public PlatoRepoPrueba() {
        Categoria catEntrada = new Categoria(1, "Entradas", "Platos ligeros para empezar");
        Categoria catFondo = new Categoria(2, "Platos de Fondo", "Especialidades principales de la casa");
        Categoria catPostre = new Categoria(3, "Postres", "Dulces tradicionales para finalizar");

        listaPlatos = new ArrayList<>(Arrays.asList(
                new Plato(1, "Ceviche Clásico", "Pescado fresco marinado en limón con ají limo", 15.50, 20, "img/CevicheClasico.jpg", catEntrada,true),
                new Plato(2, "Causa Limeña", "Masa de papa amarilla rellena de pollo y palta", 10.50, 15, "img/CausaLimeña.jpg", catEntrada,true),
                new Plato(3, "Papa a la Huancaina", "Papas sancochadas bañadas en crema de ají amarillo", 9.00, 10, "img/PapaALaHuancaina.webp", catEntrada,true),
                new Plato(4, "Anticuchos de Corazón", "Brochetas de corazón de res a la parrilla", 12.50, 15, "img/AnticuchosDeCorazon.jpg", catEntrada,true),

                new Plato(5, "Lomo Saltado", "Carne salteada al wok con cebolla, tomate y papas", 18.90, 15, "img/LomoSaltado.jpg", catFondo,true),
                new Plato(6, "Ají de Gallina", "Crema de ají amarillo con pollo y nueces", 14.00, 25, "img/AjiDeGallina.jpg", catFondo,true),
                new Plato(7, "Arroz con Pollo", "Arroz verde con cilantro acompañado de presa de pollo", 15.00, 30, "img/ArrozConPollo.webp", catFondo,true),
                new Plato(8, "Seco de Cabrito", "Guiso de tierno cabrito macerado en chicha de jora", 22.00, 45, "img/SecoDeCabrito.jpg", catFondo,true),

                new Plato(9, "Suspiro a la Limeña", "Dulce de leche con merengue al oporto", 8.00, 10, "img/SuspiroALaLimeña.jpg", catPostre,true),
                new Plato(10, "Picarones", "Anillos fritos de masa de camote y zapallo con miel", 7.50, 12, "img/Picarones.webp", catPostre,true)
        ));
    }

    @Override
    public Plato findById(int id) {
        return listaPlatos.stream()
                .filter(plato -> plato.id_plato() == id)
                .findFirst()
                .orElse(null);

    }

    @Override
    public void save(Plato plato) {
        System.out.println("Mensaje de guardado, aun no hay implementacion de base de datos" + plato.nombre());
    }

    @Override
    public void update(Plato plato) {
        System.out.println("Mensaje de actualizacion, aun no hay implementacion de base de datos" + plato.nombre());
    }

    @Override
    public void delete(int id) {
        System.out.println("Mensaje de borrado, aun no hay implementacion de base de datos");
    }

    @Override
    public List<Plato> getAll(){
        return listaPlatos;
    }
}
