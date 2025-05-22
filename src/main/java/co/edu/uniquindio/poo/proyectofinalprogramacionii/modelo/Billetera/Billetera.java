package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class Billetera implements Serializable {
    private double saldo;
    private final Usuario propietario;
    private final List<Transaccion> transacciones = new ArrayList<>();

    public void recargarBilletera(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a recargar debe ser mayor a 0");
        }
        this.saldo += monto;
    }

    public void pagarReserva(double monto, Reserva reserva) {
        if (saldo < monto) {
            throw new IllegalArgumentException("Fondos insuficientes para realizar la reserva");
        }
        this.saldo -= monto;
        transacciones.add(new Transaccion(LocalDateTime.now(), monto, reserva));
    }
}