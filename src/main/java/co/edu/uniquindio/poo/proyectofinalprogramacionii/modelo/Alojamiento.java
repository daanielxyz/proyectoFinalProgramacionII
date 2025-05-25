package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Alojamiento implements Serializable {    private String nombre;
    private Ciudad ciudad;
    private String descripcion;
    private String imagen;
    private double precioPorNocheBase;
    private int huespedesMaximos;
    private String servicios;
    private List<Reserva> reservasAlojamientoActivas;
    private List<Reserva> reservasAlojamientoHistoricas;
    private List<Reseña> reseñas;
    private List<Valoracion> valoraciones;    public Alojamiento(String nombre, Ciudad ciudad, String descripcion, String imagen,
                                                                 double precioPorNocheBase, int huespedesMaximos, String servicios) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precioPorNocheBase = precioPorNocheBase;
        this.huespedesMaximos = huespedesMaximos;
        this.servicios = servicios;
        this.reservasAlojamientoActivas = new ArrayList<>();
        this.reservasAlojamientoHistoricas = new ArrayList<>();
        this.reseñas = new ArrayList<>();
        this.valoraciones = new ArrayList<>();
    }

    // Getters y Setters manuales para compatibilidad (sin Lombok funcionando)
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Ciudad getCiudad() { return ciudad; }
    public void setCiudad(Ciudad ciudad) { this.ciudad = ciudad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public double getPrecioPorNocheBase() { return precioPorNocheBase; }
    public void setPrecioPorNocheBase(double precioPorNocheBase) { this.precioPorNocheBase = precioPorNocheBase; }

    public int getHuespedesMaximos() { return huespedesMaximos; }
    public void setHuespedesMaximos(int huespedesMaximos) { this.huespedesMaximos = huespedesMaximos; }

    public String getServicios() { return servicios; }
    public void setServicios(String servicios) { this.servicios = servicios; }

    public List<Reserva> getReservasAlojamientoActivas() { return reservasAlojamientoActivas; }
    public void setReservasAlojamientoActivas(List<Reserva> reservasAlojamientoActivas) { this.reservasAlojamientoActivas = reservasAlojamientoActivas; }

    public List<Reserva> getReservasAlojamientoHistoricas() { return reservasAlojamientoHistoricas; }
    public void setReservasAlojamientoHistoricas(List<Reserva> reservasAlojamientoHistoricas) { this.reservasAlojamientoHistoricas = reservasAlojamientoHistoricas; }

    public List<Reseña> getReseñas() { return reseñas; }
    public void setReseñas(List<Reseña> reseñas) { this.reseñas = reseñas; }

    public List<Valoracion> getValoraciones() { return valoraciones; }
    public void setValoraciones(List<Valoracion> valoraciones) { this.valoraciones = valoraciones; }

    public abstract double getPrecioPorNocheTotal();

    public void realizarReseña(String reseña) {
        reseñas.add(new Reseña(reseña));
    }    public void realizarValoracion(double valoracion) {
        valoraciones.add(new Valoracion(valoracion));
    }

    @Override
    public String toString() {
        return String.format("%s - %s ($%.2f/noche, %d huéspedes)",
                nombre, ciudad, precioPorNocheBase, huespedesMaximos);
    }
}