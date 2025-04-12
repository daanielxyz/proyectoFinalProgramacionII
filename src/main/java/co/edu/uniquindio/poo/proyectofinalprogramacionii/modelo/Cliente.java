package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Cliente {
    private String id;
    private String nombre;
    private String telefono;
    private String correo;
    private String contraseña;
}
