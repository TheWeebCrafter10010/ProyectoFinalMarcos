package alonso.benito.proyectofinalmarcos.Servicios;

import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioRegistro {

    private final UsuarioRepository usuarioRepo;

    private final ServicioEmail servicioEmail;



    public ServicioRegistro(UsuarioRepository usuarioRepo,ServicioEmail servicioEmail) {
        this.usuarioRepo = usuarioRepo;
        this.servicioEmail = servicioEmail;
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

        String asunto = "Bienvenido a Sabor y Tradición";
        String cuerpo = String.format(servicioEmail.getBienvenidaCorreo(), usuario.getNombre());
        servicioEmail.enviarEmail(usuario.getEmail(), asunto, cuerpo);

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
