package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Valoracion implements Serializable {
    private double puntuacion;

    public Valoracion(double puntuacion) {
        this.puntuacion = puntuacion;
    }
}