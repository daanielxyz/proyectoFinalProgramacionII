package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class Transaccion implements Serializable {
    private String id;
    private double monto;
    private LocalDateTime fecha;
    private String tipo;
    private Reserva reserva;

    public Transaccion(String id, double monto, String tipo, Reserva reserva) {
        this.id = id;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
        this.tipo = tipo;
        this.reserva = reserva;
    }
}