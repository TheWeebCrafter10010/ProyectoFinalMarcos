package alonso.benito.proyectofinalmarcos.Servicios;

import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicioRegistro {

    private final UsuarioRepository usuarioRepo;

    public ServicioRegistro(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }
    public boolean registrarUsuario(Usuario usuario) {
        boolean emailValido = validarFormatoEmail(usuario.getEmail());
        if (!emailValido) {
            return false; // Formato de email no válido
        }
        Usuario usuarioExistente = usuarioRepo.findByEmail(usuario.getEmail());
        if (usuarioExistente != null) {
            return false; // El email ya está registrado
        }
        usuarioRepo.save(usuario);
        return true; // Registro exitoso
    }

    public Usuario loginUsuario(String email, String password) {
        Usuario usuario = usuarioRepo.findByEmail(email);
        if(usuario==null) {
            return null; // Usuario no encontrado
        }
        if (usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;//Contraseña incorrecta
    }

    private boolean validarFormatoEmail(String email) {
        return email.contains("@") && email.contains(".");

    }

}
