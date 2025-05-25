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
                 double precioPorNocheBase, int huespedesMaximos, String servicios) {
        super(nombre, ciudad, descripcion, imagen, precioPorNocheBase, huespedesMaximos, servicios);
        this.habitaciones = new ArrayList<>();
    }

    // Getter y Setter manual para compatibilidad
    public List<Habitacion> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(List<Habitacion> habitaciones) { this.habitaciones = habitaciones; }

    @Override
    public double getPrecioPorNocheTotal() {
        return getPrecioPorNocheBase();
    }

    public double getPrecioPorNocheTotal(Habitacion habitacion) {
        return habitacion.getPrecio();
    }    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    @Override
    public String toString() {
        return String.format("Hotel: %s - %s ($%.2f/noche, %d huéspedes, %d habitaciones)",
                getNombre(), getCiudad(), getPrecioPorNocheBase(), getHuespedesMaximos(),
                habitaciones != null ? habitaciones.size() : 0);
    }
}