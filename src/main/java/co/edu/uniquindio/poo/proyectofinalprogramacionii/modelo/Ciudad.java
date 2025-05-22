package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Ciudad implements Serializable {
    private String nombre;
}
