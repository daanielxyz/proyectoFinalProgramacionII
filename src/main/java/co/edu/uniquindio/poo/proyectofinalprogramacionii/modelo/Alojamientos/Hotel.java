package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Hotel extends Alojamiento implements Serializable {
    private List<Habitacion> habitaciones;

    public Hotel(double precioPorNoche, int huespedesMaximos, String nombre, Ciudad ciudad, String descripcion,
                 String imagen, List<String> serviciosDisponibles, List<Habitacion> habitaciones) {
        super(0.0, nombre, ciudad, descripcion, imagen, precioPorNoche, huespedesMaximos, serviciosDisponibles);
        this.habitaciones = habitaciones != null ? habitaciones : new ArrayList<>();
    }

    public double getPrecioPorNocheTotal(Habitacion habitacion) {
        return habitacion != null ? habitacion.getPrecio() : 0.0;
    }
}