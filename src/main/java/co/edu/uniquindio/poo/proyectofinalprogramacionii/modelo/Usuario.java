package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Builder
@Getter
@Setter
public class Usuario implements Serializable {
    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private String email;
    private String contrasena;
    private boolean activo;
    private Billetera billetera;
}