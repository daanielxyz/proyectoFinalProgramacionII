package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Casa extends Alojamiento {
    private double costoMantenimiento;

    public Casa(String nombre, Ciudad ciudad, String descripcion, String imagen,
                double precioPorNocheBase, int huespedesMaximos, double costoMantenimiento) {
        super(nombre, ciudad, descripcion, imagen, precioPorNocheBase, huespedesMaximos);
        this.costoMantenimiento = costoMantenimiento;
    }

    @Override
    public double getPrecioPorNocheTotal() {
        return getPrecioPorNocheBase() + costoMantenimiento;
    }
}