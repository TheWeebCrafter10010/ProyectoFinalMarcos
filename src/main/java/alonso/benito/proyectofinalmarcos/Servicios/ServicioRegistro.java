package alonso.benito.proyectofinalmarcos.Servicios;

import alonso.benito.proyectofinalmarcos.Modelos.Resena;
import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.IBasicRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ServicioRegistro {

    private final IBasicRepository<Usuario, String> usuarioRepo;
    private int nuevoId = 10; //ID autoincremental, cambiar luego
    ServicioRegistro(@Qualifier("usuarioRepoPrueba") IBasicRepository<Usuario, String> usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public boolean registrarUsuario(String nombre, String email, String password) {
        boolean emailValido = validarFormatoEmail(email);
        if (!emailValido) {
            return false; // Formato de email no válido
        }
        Usuario usuarioExistente = buscarUsuarioPorEmail(email);
        if (usuarioExistente != null) {
            return false; // El email ya está registrado
        }
        Usuario nuevoUsuario = new Usuario(nuevoId++, nombre, email, password,new ArrayList<>(),new ArrayList<>());
        usuarioRepo.save(nuevoUsuario);
        return true; // Registro exitoso
    }

    public Usuario loginUsuario(String email, String password) {
        Usuario usuario = buscarUsuarioPorEmail(email);
        if(usuario==null) {
            return null; // Usuario no encontrado
        }
        if (usuario.password().equals(password)) {
            return usuario;
        }
        return null;//Contraseña incorrecta
    }

    private boolean validarFormatoEmail(String email) {
        return email.contains("@") && email.contains(".");

    }
    private Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepo.findById(email);
    }

}
