package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Usuario implements Serializable {
    private String cedula;
    private String nombre;
    private String telefono;
    private String email;
    private String contraseña;
    private Billetera billetera;
    private String codigoActivacion;
    private boolean cuentaActiva;

    public Usuario(String cedula, String nombre, String telefono, String email, String contraseña) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.contraseña = contraseña;
        this.billetera = new Billetera(UUID.randomUUID().toString(), this);
        this.cuentaActiva = false;
    }
}