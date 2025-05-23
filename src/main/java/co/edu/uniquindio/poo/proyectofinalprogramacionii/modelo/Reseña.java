package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Reseña implements Serializable {
    private String comentario;
    private String id;

    public Reseña(String comentario) {
        this.comentario = comentario;
    }
}