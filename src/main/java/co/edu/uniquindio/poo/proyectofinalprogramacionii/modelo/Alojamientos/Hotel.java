package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;

import java.util.List;

public class Hotel extends Alojamiento {

    private List<Habitacion> habitaciones;

    public Hotel(String nombre, Ciudad ciudad, String descripcion, String imagen, String precioPorNoche, int huespedesMaximos, List<String> serviciosDisponibles, List<Habitacion> habitaciones) {
        super(nombre, ciudad, descripcion, imagen, precioPorNoche, huespedesMaximos, serviciosDisponibles);
        this.habitaciones = habitaciones;
    }


}
