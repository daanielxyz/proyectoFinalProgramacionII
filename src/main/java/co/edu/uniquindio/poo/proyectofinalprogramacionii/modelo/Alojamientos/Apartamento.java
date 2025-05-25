package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Apartamento extends Alojamiento {
    private double costoMantenimiento;

    public Apartamento(String nombre, Ciudad ciudad, String descripcion, String imagen,
                       double precioPorNocheBase, int huespedesMaximos, String servicios, double costoMantenimiento) {
        super(nombre, ciudad, descripcion, imagen, precioPorNocheBase, huespedesMaximos, servicios);
        this.costoMantenimiento = costoMantenimiento;
    }

    // Getter y Setter manual para compatibilidad
    public double getCostoMantenimiento() { return costoMantenimiento; }
    public void setCostoMantenimiento(double costoMantenimiento) { this.costoMantenimiento = costoMantenimiento; }

    @Override
    public double getPrecioPorNocheTotal() {
        return getPrecioPorNocheBase() + costoMantenimiento;
    }

    @Override
    public String toString() {
        return String.format("Apartamento: %s - %s ($%.2f + $%.2f mantenimiento/noche, %d huéspedes)",
                getNombre(), getCiudad(), getPrecioPorNocheBase(), costoMantenimiento, getHuespedesMaximos());
    }
}