package alonso.benito.proyectofinalmarcos.configs;

import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.UsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginExitosoHandler implements AuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;

    public LoginExitosoHandler(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            request.getSession().setAttribute("usuario", usuario);
        }

        String redirectUrl = "/index";

        boolean esAdmin = authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (esAdmin) {
            redirectUrl = "/admin/dashboard";
        } else {
            redirectUrl = "/index";
        }
        response.sendRedirect(redirectUrl);
    }
}