package alonso.benito.proyectofinalmarcos.Repositorios;

import alonso.benito.proyectofinalmarcos.Modelos.Resena;
import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("usuarioRepoPrueba")
public class UsuarioRepoPrueba implements IBasicRepository <Usuario,String>{


    private ResenaRepoPrueba resenaRepoPrueba;

    private List<Usuario> usuarios = new ArrayList<>(10);
    UsuarioRepoPrueba(ResenaRepoPrueba resenaRepoPrueba) {
        this.resenaRepoPrueba = resenaRepoPrueba;
        usuarios.add(new Usuario(1, "Alejandro Ruiz", "a.ruiz@hotmail.com", "ARuiz2026",new ArrayList<Resena>(),new ArrayList<>()));
        usuarios.add(new Usuario(2, "Beatriz Sáenz", "beatriz.saenz@gmail.com", "B3atriz.sec",new ArrayList<Resena>(),new ArrayList<>()));
        usuarios.add(new Usuario(3, "Cristian Torres", "ctorres@outlook.com", "CrisT#9921",new ArrayList<Resena>(),new ArrayList<>()));
        usuarios.add(new Usuario(4, "Daniela Méndez", "d.mendez@empresa.es", "Admin_Dany26",new ArrayList<Resena>(),new ArrayList<>()));
        usuarios.add(new Usuario(5, "Eduardo Vega", "evega88@gmail.com", "EduV-pass123",new ArrayList<Resena>(),new ArrayList<>()));
        usuarios.add(new Usuario(6, "Fernanda Luna", "fer.luna@icloud.com", "LunaFer*99",new ArrayList<Resena>(),new ArrayList<>()));
        usuarios.add(new Usuario(7, "Gabriel Soto", "gsoto.dev@yahoo.com", "GaboDev_44",new ArrayList<Resena>(),new ArrayList<>()));
        usuarios.add(new Usuario(8, "Helena Rivas", "h.rivas@servicio.net", "H_rivas.2024",new ArrayList<Resena>(),new ArrayList<>()));
        usuarios.add(new Usuario(9, "Iván Castillo", "icastillo@dominio.org", "IvanC_strong!",new ArrayList<Resena>(),new ArrayList<>()));
        usuarios.add(new Usuario(10, "Julieta Peña", "juli.pena@gmail.com", "JP_secure99",new ArrayList<Resena>(),new ArrayList<>()));

        //Agregamos algunas reseñas a los usuarios para probar la funcionalidad de mostrar reseñas en la página de inicio
        usuarios.get(0).resenas().add(resenaRepoPrueba.getTopResenas(5).get(0));
        usuarios.get(1).resenas().add(resenaRepoPrueba.getTopResenas(5).get(1));
        usuarios.get(3).resenas().add(resenaRepoPrueba.getTopResenas(5).get(2));
        usuarios.get(6).resenas().add(resenaRepoPrueba.getTopResenas(5).get(3));
        usuarios.get(9).resenas().add(resenaRepoPrueba.getTopResenas(5).get(4));
    }

    @Override
    public Usuario findById(String email) {
        return usuarios.stream()
                .filter(usuario -> usuario.email().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Usuario entity) {
        usuarios.add(entity);
    }

    @Override
    public void update(Usuario entity) {
        usuarios.removeIf(usuario -> usuario.id_usuario() == entity.id_usuario());
        usuarios.add(entity);
    }

    @Override
    public void delete(String email) {
        usuarios.removeIf(usuario -> usuario.email().equals(email));
    }
}
