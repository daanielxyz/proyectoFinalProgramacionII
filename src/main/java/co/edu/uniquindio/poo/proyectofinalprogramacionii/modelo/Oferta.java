package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Oferta implements Serializable {
    private UUID id = UUID.randomUUID();
    private String alojamientoNombre;
    private double descuento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}