package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Alojamiento implements Serializable {
    private double precioPorNocheTotal;
    private List<Reseña> listaReseñas;
    private List<Valoracion> listaValoraciones;
    private List<Reserva> reservasAlojamientoHistoricas;
    private List<Reserva> reservasAlojamientoActivas;
    private String nombre;
    private Ciudad ciudad;
    private String descripcion;
    private String imagen;
    private double precioPorNoche;
    private int huespedesMaximos;
    private List<String> serviciosDisponibles;

    protected Alojamiento(double precioPorNocheTotal, String nombre, Ciudad ciudad, String descripcion, String imagen,
                          double precioPorNoche, int huespedesMaximos, List<String> serviciosDisponibles) {
        this.precioPorNocheTotal = precioPorNocheTotal;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precioPorNoche = precioPorNoche;
        this.huespedesMaximos = huespedesMaximos;
        this.serviciosDisponibles = serviciosDisponibles != null ? serviciosDisponibles : new ArrayList<>();
        this.listaReseñas = new ArrayList<>();
        this.listaValoraciones = new ArrayList<>();
        this.reservasAlojamientoHistoricas = new ArrayList<>();
        this.reservasAlojamientoActivas = new ArrayList<>();
    }

    public void realizarReseña(String reseña) throws Exception {
        if (reseña.isEmpty()) {
            throw new Exception("La reseña no puede estar vacía");
        }
        listaReseñas.add(new Reseña(reseña, generarId()));
    }

    public void realizarValoracion(int valoracion) throws Exception {
        if (valoracion < 0 || valoracion > 5) {
            throw new Exception("Ingrese una valoración entre 0 y 5");
        }
        listaValoraciones.add(new Valoracion(valoracion));
    }

    private String generarId() {
        return java.util.UUID.randomUUID().toString();
    }
}