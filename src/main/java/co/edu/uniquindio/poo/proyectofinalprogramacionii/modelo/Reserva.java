package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Builder
@Getter
@Setter
public class Reserva implements Serializable {
    private Usuario clienteHospedado;
    private Alojamiento alojamientoReservado;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private int numHuespedes;

    public static double calcularNochesDeReserva(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) throws Exception {
        double noches = ChronoUnit.DAYS.between(fechaEntrada.toLocalDate(), fechaSalida.toLocalDate());
        if (noches <= 0) {
            throw new Exception("La fecha de salida debe ser posterior a la de entrada.");
        }
        return noches;
    }
}