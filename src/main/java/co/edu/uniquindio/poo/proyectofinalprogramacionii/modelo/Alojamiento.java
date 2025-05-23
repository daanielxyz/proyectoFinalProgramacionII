package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Alojamiento implements Serializable {
    private String nombre;
    private Ciudad ciudad;
    private String descripcion;
    private String imagen;
    private double precioPorNocheBase;
    private int huespedesMaximos;
    private List<Reserva> reservasAlojamientoActivas;
    private List<Reserva> reservasAlojamientoHistoricas;
    private List<Reseña> reseñas;
    private List<Valoracion> valoraciones;

    public Alojamiento(String nombre, Ciudad ciudad, String descripcion, String imagen,
                       double precioPorNocheBase, int huespedesMaximos) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precioPorNocheBase = precioPorNocheBase;
        this.huespedesMaximos = huespedesMaximos;
        this.reservasAlojamientoActivas = new ArrayList<>();
        this.reservasAlojamientoHistoricas = new ArrayList<>();
        this.reseñas = new ArrayList<>();
        this.valoraciones = new ArrayList<>();
    }

    public abstract double getPrecioPorNocheTotal();

    public void realizarReseña(String reseña) {
        reseñas.add(new Reseña(reseña));
    }

    public void realizarValoracion(double valoracion) {
        valoraciones.add(new Valoracion(valoracion));
    }
}