package alonso.benito.proyectofinalmarcos.Repositorios;

import alonso.benito.proyectofinalmarcos.Modelos.Resena;

import java.util.List;

public interface IResenasRepository {
    List<Resena> getTopResenas(int cantidad);

}
