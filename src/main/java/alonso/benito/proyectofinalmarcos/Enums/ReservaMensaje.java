package alonso.benito.proyectofinalmarcos.Enums;

public enum ReservaMensaje {
    RESERVA_EXITOSA("Reserva realizada con éxito"),
    ERROR_SIN_MESAS("No hay mesas disponibles para la reserva"),
    ERROR_RESERVA("Ocurrió un error al realizar la reserva");

    private final String mensaje;

    ReservaMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
