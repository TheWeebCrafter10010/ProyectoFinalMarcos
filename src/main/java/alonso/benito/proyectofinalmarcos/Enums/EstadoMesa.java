package alonso.benito.proyectofinalmarcos.Enums;

public enum EstadoMesa {
    DISPONIBLE("Mesa disponible"),
    RESERVADA ("Mesa reservada"),
    MANTENIMIENTO ("Mesa en mantenimiento");

    private final String mensaje;
    EstadoMesa(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getMensaje() {
        return mensaje;
    }
}
