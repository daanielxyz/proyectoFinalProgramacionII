package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Factura implements Serializable {
    private UUID id;
    private double subtotal;
    private double total;
    private LocalDateTime fecha;
    private Reserva reserva;

    public Factura() {
        this.id = UUID.randomUUID();
    }
}