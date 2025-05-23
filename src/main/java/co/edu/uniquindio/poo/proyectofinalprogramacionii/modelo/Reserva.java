package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class Reserva implements Serializable {
    private String id;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private Usuario usuario;
    private Alojamiento alojamientoReservado;
    private int numeroHuespedes;
    private Habitacion habitacion;

    public Reserva(String id, LocalDate fechaEntrada, LocalDate fechaSalida, Usuario usuario,
                   Alojamiento alojamientoReservado, int numeroHuespedes, Habitacion habitacion) {
        this.id = id;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.usuario = usuario;
        this.alojamientoReservado = alojamientoReservado;
        this.numeroHuespedes = numeroHuespedes;
        this.habitacion = habitacion;
    }

    public static double calcularNochesDeReserva(LocalDate fechaEntrada, LocalDate fechaSalida) {
        return ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
    }
}