package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class Habitacion implements Serializable {
    private int numHabitacion;
    private double precio;
    private int capacidadMaximaHuespedes;
    private String imagenHabitacion;
    private String descripcionHabitacion;
}