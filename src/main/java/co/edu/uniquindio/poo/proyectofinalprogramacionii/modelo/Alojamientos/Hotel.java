package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Hotel extends Alojamiento {
    private List<Habitacion> habitaciones;

    public Hotel(String nombre, Ciudad ciudad, String descripcion, String imagen,
                 double precioPorNocheBase, int huespedesMaximos) {
        super(nombre, ciudad, descripcion, imagen, precioPorNocheBase, huespedesMaximos);
        this.habitaciones = new ArrayList<>();
    }

    @Override
    public double getPrecioPorNocheTotal() {
        return getPrecioPorNocheBase();
    }

    public double getPrecioPorNocheTotal(Habitacion habitacion) {
        return habitacion.getPrecio();
    }

    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }
}