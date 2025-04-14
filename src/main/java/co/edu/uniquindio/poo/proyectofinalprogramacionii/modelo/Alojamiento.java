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
        private LocalDateTime fechaEntrada;
        private LocalDateTime fechaSalida;
        private  int numHuespedes;
        private double valorEstanciaTotal;
    //ATRIBUTOS OBLIGATORIOS
        @NonNull private String nombre;
        @NonNull private Ciudad ciudad;
        @NonNull private String descripcion;
        @NonNull private String imagen;
        @NonNull private String precioPorNoche;
        @NonNull private int huespedesMaximos;
        @NonNull private List<String> serviciosDisponibles;

}
