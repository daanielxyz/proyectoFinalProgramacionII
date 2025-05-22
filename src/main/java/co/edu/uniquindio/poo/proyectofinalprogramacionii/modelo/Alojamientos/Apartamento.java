package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Apartamento extends Alojamiento implements Serializable {
    private double costoMantenimiento;

    public Apartamento(double precioPorNoche, int huespedesMaximos, String nombre, Ciudad ciudad, String descripcion,
                       String imagen, List<String> serviciosDisponibles, double costoMantenimiento) {
        super(precioPorNoche + costoMantenimiento, nombre, ciudad, descripcion, imagen, precioPorNoche,
                huespedesMaximos, serviciosDisponibles);
        this.costoMantenimiento = costoMantenimiento;
    }

    @Override
    public double getPrecioPorNocheTotal() {
        return getPrecioPorNoche() + costoMantenimiento;
    }
}