package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@RequiredArgsConstructor
public class Reserva {
    //ATRIBUTOS OBLIGATORIOS
        @NonNull private Cliente clienteHospedado;
        @NonNull private Alojamiento alojamientoReservado;
        @NonNull private LocalDateTime fechaEntrada;
        @NonNull private LocalDateTime fechaSalida;
        @NonNull private  int numHuespedes;

    //METODOS DE LA RESERVA
        public static double calcularNochesDeReserva(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) throws Exception{
            double noches = ChronoUnit.DAYS.between(fechaEntrada.toLocalDate(), fechaSalida.toLocalDate());
            if (noches <= 0) {
                throw new Exception("La fecha de salida debe ser posterior a la de entrada.");
            }
            return noches;
        }
}
