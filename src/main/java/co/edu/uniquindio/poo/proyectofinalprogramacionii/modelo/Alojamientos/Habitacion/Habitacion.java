package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Habitacion implements Serializable {
    private String id;
    private double precio;
    private int capacidad;

    public Habitacion(String id, double precio, int capacidad) {
        this.id = id;
        this.precio = precio;
        this.capacidad = capacidad;
    }
}