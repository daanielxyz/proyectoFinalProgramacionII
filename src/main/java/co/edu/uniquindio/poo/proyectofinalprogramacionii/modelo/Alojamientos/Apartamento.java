package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;

import java.util.List;

public class Apartamento extends Alojamiento {

    private double costoAseoYMantenimiento;

    public Apartamento(String nombre, Ciudad ciudad, String descripcion, String imagen, double precioPorNoche, int huespedesMaximos, List<String> serviciosDisponibles, double costoAseoYMantenimiento){
        super(nombre, ciudad, descripcion, imagen, precioPorNoche, huespedesMaximos, serviciosDisponibles);
        this.costoAseoYMantenimiento = costoAseoYMantenimiento;
        setPrecioPorNocheTotal(precioPorNoche + costoAseoYMantenimiento);
    }
}
