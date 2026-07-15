package alonso.benito.proyectofinalmarcos.Controladores;

import alonso.benito.proyectofinalmarcos.Modelos.Reserva;
import alonso.benito.proyectofinalmarcos.Servicios.ServicioChatbot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatbotController {

    @Autowired
    ServicioChatbot servicioChatbot;

    @PostMapping("/chatbot")
    public Map<String, String> responderPregunta(@RequestBody Map<String, String> peticion) {

        String mensajeCliente = peticion.get("mensaje");
        return servicioChatbot.responderChat(mensajeCliente);

    }
}
