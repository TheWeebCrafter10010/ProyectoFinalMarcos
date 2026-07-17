package alonso.benito.proyectofinalmarcos.Servicios;

import alonso.benito.proyectofinalmarcos.Modelos.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class ServicioEmail {
    @Autowired
    private JavaMailSender mailSender;

    private final String correoOrigen = "alonso89benito2@gmail.com";

    private final String bienvenidaCorreo = """
    ¡Hola, %s! 🌟
    
    Te damos una cálida bienvenida a Sabor y Tradición. 
    
    Nos alegra muchísimo que te hayas unido a nuestra comunidad. A partir de ahora, estás a un solo clic de disfrutar de los sabores más auténticos, recetas con historia y el cariño que le ponemos a cada plato de nuestra cocina tradicional.
    
    Para agradecer tu registro, te invitamos a explorar nuestro menú y descubrir la verdadera tradición culinaria.
    
    ¡Buen provecho y bienvenido a la familia!
    
    Atentamente,
    El equipo de Sabor y Tradición.
    """;

    private final String confirmacionReservaCorreo = """
    ¡Tu reserva ha sido registrada con éxito! 🍽️✨
    
    Hola, %s.
    
    Te confirmamos que hemos recibido y registrado los datos de tu reserva en Sabor y Tradición. Estamos preparando todo para que disfrutes de una experiencia gastronómica inolvidable.
    
    Detalles de tu reserva:
    ---------------------------------------------
    📅 Fecha:   %s
    ⏰ Hora:    %s
    🪑 Mesa:    Nº %d
    ---------------------------------------------
    
    Te sugerimos llegar con 10 minutos de anticipación. Si necesitas realizar algún cambio en tu reserva o tienes alguna solicitud especial (alergias, trona para bebés, etc.), no dudes en contactarnos respondiendo a este correo.
    
    ¡Nos vemos pronto para compartir lo mejor de nuestro sabor y tradición!
    
    Atentamente,
    El equipo de Sabor y Tradición.
    """;

    private final String cancelacionReservaAdmin = """
        Lamentamos informarte que tu reserva ha sido cancelada. 😔
        
        Detalles de la reserva:
        - Fecha: %s
        - Hora: %s
        - Mesa: %s
        
        Si tienes alguna pregunta, no dudes en contactarnos.
        """;
    private final String cancelacionReservaUsuario = """
        Hola, hemos recibido tu solicitud y confirmamos que tu reserva ha sido cancelada correctamente. ✅
        
        Estos son los detalles de la reserva que se eliminó:
        - Fecha: %s
        - Hora: %s
        - Mesa: %s
        
        Lamentamos que no puedas acompañarnos en esta ocasión, ¡esperamos verte pronto en Sabor y Tradición!
    """;

    @Async
    public void enviarEmail(String destinatario, String asunto, String cuerpo) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(correoOrigen);
            mail.setTo(destinatario);
            mail.setSubject(asunto);
            mail.setText(cuerpo);

            mailSender.send(mail);
            System.out.println("Correo enviado exitosamente a: " + destinatario);
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    public String getBienvenidaCorreo() {
        return bienvenidaCorreo;
    }
    public String getConfirmacionReservaCorreo(Reserva reserva) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaFormateada = reserva.getFecha().format(formatoFecha);

        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormateada = reserva.getHora().format(formatoHora);

        String cuerpo = String.format(
                confirmacionReservaCorreo,
                reserva.getUsuario().getNombre(),
                fechaFormateada,
                horaFormateada,
                reserva.getMesa().getIdMesa()
        );
        return cuerpo;
    }

    public String getMensajeCancelacionReserva(Reserva reserva, boolean isAdmin) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaFormateada = reserva.getFecha().format(formatoFecha);

        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormateada = reserva.getHora().format(formatoHora);

        String mensajeCancelacion = isAdmin ? cancelacionReservaAdmin : cancelacionReservaUsuario;
        String cuerpo = String.format(
                mensajeCancelacion,
                fechaFormateada,
                horaFormateada,
                reserva.getMesa().getIdMesa()
        );
        return cuerpo;
    }
}
