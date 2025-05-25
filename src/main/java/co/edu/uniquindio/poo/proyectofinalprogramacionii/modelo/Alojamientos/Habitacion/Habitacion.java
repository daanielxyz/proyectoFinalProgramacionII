package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Habitacion implements Serializable {
    private String id;
    private double precio;
    private int capacidad;


    @Override
    public String toString() {
        return String.format("Habitación %s - $%.2f/noche (%d personas)",
                id, precio, capacidad);
    }
}