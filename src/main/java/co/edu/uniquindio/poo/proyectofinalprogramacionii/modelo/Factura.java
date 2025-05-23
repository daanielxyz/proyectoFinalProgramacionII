package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Factura implements Serializable {
    private String id;
    private double subtotal;
    private double total;
    private LocalDateTime fecha;
    private Reserva reserva;

    public Factura(double subtotal, double total, LocalDateTime fecha, Reserva reserva) {
        this.id = UUID.randomUUID().toString();
        this.subtotal = subtotal;
        this.total = total;
        this.fecha = fecha;
        this.reserva = reserva;
    }
}