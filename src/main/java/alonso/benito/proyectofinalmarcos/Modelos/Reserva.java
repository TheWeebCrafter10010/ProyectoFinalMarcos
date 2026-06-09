package alonso.benito.proyectofinalmarcos.Modelos;

import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private static int contador = 1000;

    private int idReserva;
    private String nombreCliente;
    private String telefono;
    private String fecha;
    private String hora;
    private int cantidadPersonas;

    private List<Plato> platosPedidos;

    private Mesa mesaAsignada;

    public Reserva() {
        this.idReserva = ++contador;
        this.platosPedidos = new ArrayList<>();
    }

    public Reserva(String nombreCliente, String telefono, String fecha, String hora, int cantidadPersonas) {
        this.idReserva = ++contador;
        this.nombreCliente = nombreCliente;
        this.telefono = telefono;
        this.fecha = fecha;
        this.hora = hora;
        this.cantidadPersonas = cantidadPersonas;
        this.platosPedidos = new ArrayList<>();

    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public List<Plato> getPlatosPedidos() {
        return platosPedidos;
    }

    public void setPlatosPedidos(List<Plato> platosPedidos) {
        this.platosPedidos = platosPedidos;
    }

    public Mesa getMesaAsignada() {
        return mesaAsignada;
    }

    public void setMesaAsignada(Mesa mesaAsignada) {
        this.mesaAsignada = mesaAsignada;
    }

}
