package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Transaccion implements Serializable {
    private LocalDateTime fecha;
    private double monto;
    private Reserva reserva;
    private UUID identificador;

    public Transaccion(LocalDateTime fecha, double monto, Reserva reserva) {
        this.fecha = fecha;
        this.monto = monto;
        this.reserva = reserva;
        this.identificador = UUID.randomUUID();
    }
}