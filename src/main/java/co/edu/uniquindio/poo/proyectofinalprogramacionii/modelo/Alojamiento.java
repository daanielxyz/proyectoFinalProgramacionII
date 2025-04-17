package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public abstract class Alojamiento {
    //ATRIBUTOS NO OBLIGATORIOS
        private boolean estaHospedado;
        private double precioPorNocheTotal;
        private List<Reseña> listaReseñas;
        private List<Valoracion> listaValoraciones;

    //ATRIBUTOS OBLIGATORIOS
        @NonNull private String nombre;
        @NonNull private Ciudad ciudad;
        @NonNull private String descripcion;
        @NonNull private String imagen;
        @NonNull private double precioPorNoche;
        @NonNull private int huespedesMaximos;
        @NonNull private List<String> serviciosDisponibles;

    //METODOS DEL ALOJAMIENTO

        //REALIZAR RESEÑA
            public void realizarReseña(String reseña) throws Exception {
                if (reseña.isEmpty()) {
                    throw new Exception("La reseña no puede estar vacía");
                }
                Reseña reseñaRealizada = new Reseña(reseña);
                listaReseñas.add(reseñaRealizada);
            }

        //REALIZAR VALORACIÓN
            public void realizarValoracion(int valoracion) throws Exception{
                if (valoracion < 0 || valoracion > 5) {
                    throw new Exception("Ingrese una valoracion entre 0 y 5");
                }
                Valoracion valoracionRealizada = new Valoracion(valoracion);
                listaValoraciones.add(valoracionRealizada);
            }



}
