package alonso.benito.proyectofinalmarcos.configs;

import alonso.benito.proyectofinalmarcos.Modelos.Usuario;
import alonso.benito.proyectofinalmarcos.Repositorios.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final LoginExitosoHandler loginExitosoHandler;

    public SecurityConfig(LoginExitosoHandler loginExitosoHandler) {
        this.loginExitosoHandler = loginExitosoHandler;
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/menu", "/usuarios/login", "/usuarios/registro",
                                "/usuarios/guardar", "/img/**", "/css/**", "/js/**", "/error","/api/chatbot").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/usuarios/login")
                        .loginProcessingUrl("/usuarios/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(loginExitosoHandler)
                        .failureUrl("/usuarios/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/usuarios/logout")
                        .logoutSuccessUrl("/index?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return email -> {
            Usuario usuario = usuarioRepository.findByEmail(email);
            if (usuario == null) {
                throw new org.springframework.security.core.userdetails.UsernameNotFoundException("Usuario no encontrado");
            }

            String rol = (usuario.getRol() != null) ? usuario.getRol() : "USUARIO";
            return User.withUsername(usuario.getEmail())
                    .password(usuario.getPassword())
                    .roles(rol)
                    .build();
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return bcrypt.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (encodedPassword == null) return false;
                if (encodedPassword.startsWith("$2a$") || encodedPassword.startsWith("$2b$") || encodedPassword.startsWith("$2y$")) {
                    return bcrypt.matches(rawPassword, encodedPassword);
                }
                // Compatibilidad temporal con los registros antiguos del script SQL.
                return encodedPassword.contentEquals(rawPassword);
            }
        };
    }
}
