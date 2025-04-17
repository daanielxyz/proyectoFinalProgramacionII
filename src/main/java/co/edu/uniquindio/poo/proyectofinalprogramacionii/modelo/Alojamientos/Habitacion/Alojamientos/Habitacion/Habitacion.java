package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Alojamientos.Habitacion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Habitacion {
    private int numHabitacion;
    private double precio;
    private int capacidadMaximaHuespedes;
    private String imagenHabitacion;
    private String descripcionHabitacion;
}
