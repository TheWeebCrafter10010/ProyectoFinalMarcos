package alonso.benito.proyectofinalmarcos.Modelos;

import alonso.benito.proyectofinalmarcos.Enums.EstadoMesa;
import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa")
    private int idMesa;

    private int numero;
    private int capacidad;

    @Enumerated(EnumType.STRING)
    private EstadoMesa estado;//DISPONIBLE, RESERVADA, MANTENIMIENTO

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoMesa getEstado() {
        return estado;
    }

    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }
}
